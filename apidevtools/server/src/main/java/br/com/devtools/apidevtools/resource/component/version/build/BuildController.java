package br.com.devtools.apidevtools.resource.component.version.build;

import static br.com.devtools.apidevtools.core.permission.PermissionMethod.ALL;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.controller.Filter;
import br.com.devtools.apidevtools.core.crypto.Crypto;
import br.com.devtools.apidevtools.core.permission.PermissionClass;
import br.com.devtools.apidevtools.core.permission.PermissionMethod;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.resource.component.version.Version;
import br.com.devtools.apidevtools.resource.component.version.VersionController;
import br.com.devtools.apidevtools.resource.component.version.build.hash.BuildHash;
import br.com.devtools.apidevtools.resource.component.version.build.hash.BuildHashStatus;
import io.swagger.annotations.Api;

@Api("Compilação")
@Path("component/{componentId}/version/{versionId}/build")
@PermissionClass(description="Compilação")
public class BuildController extends Controller<Build> {

	private static final String UPLOAD = "UPLOAD";
	private static final String DOWNLOAD = "DOWNLOAD";
	private static final String GERAHASH = "GERAHASH";
	
	@PathParam("componentId")
	private Long componentId;
	
	@PathParam("versionId")
	private Long versionId;

	@Override
	public Class<Build> getClasse() {
		return Build.class;
	}

	private Version version = null;

	public Version getVersion() throws RestException {

		if (this.version == null) {

			VersionController versionController = new VersionController();
			versionController.setSessao(this.getSessao());
			versionController.setContext(this.getContext());
			versionController.setComponentId(this.getComponentId());
			this.version = versionController.get(this.getVersionId());

			if (this.version == null) {
				throw new RestException("Version com código " + this.getVersionId() + " não encontrado.");
			}

		}

		return this.version;

	}

	@Override
	protected List<Filter> getFilters() throws RestException {

		List<Filter> filters = new ArrayList<>();
		filters.add(new Filter("version", this.getVersion()));

		return filters;

	}

	@Override
	protected Build afterGet(Build model) throws RestException {
		if (model != null && !this.getVersion().getId().equals(model.getVersion().getId())) {
			return null;
		}
		return model;
	}

	@Override
	protected void beforePost(Build model) throws RestException {
		model.setStatus(BuildStatus.APPROVED);
		model.setVersion(this.getVersion());
	}

	@Override
	protected void beforePut(Build model) throws RestException {
		model.setVersion(this.getVersion());
	}

	@Override
	protected void beforeRemove(Build model) throws RestException {
		if (model != null && !this.getVersion().getId().equals(model.getVersion().getId())) {
			throw new RestException("Esta Build não pertence a Version " + this.getVersion().getId() + ".");
		}
	}

	@POST
	@Path("{id}/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@PermissionMethod(types=UPLOAD, description="Upload de Arquivo")
	public Response upload(MultipartFormDataInput multipartFormDataInput, @PathParam("id") Long buildId) throws RestException {
		
		try {
			
			Map<String, List<InputPart>> map = multipartFormDataInput.getFormDataMap();
			List<InputPart> lstInputPart = map.get("uploadedFile");
			
			InputStream is = null;
			
			for (InputPart inputPart : lstInputPart) {
				is = inputPart.getBody(InputStream.class, null);
			}
			try (ByteArrayOutputStream os = new ByteArrayOutputStream();)
		    {
		        byte[] buffer = new byte[0xFFFF];

		        for (int len; (len = is.read(buffer)) != -1;)
		            os.write(buffer, 0, len);

		        os.flush();
		        
		        this.upload(os.toByteArray(), buildId);
		    }
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException(e);
		}
		
		return Response.ok().build();
	}
	
	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@PermissionMethod(types=UPLOAD, description="Upload de Arquivo")
	public Response createBuildAndUpload(MultipartFormDataInput multipartFormDataInput) throws RestException {
		
		try {
			
			Map<String, List<InputPart>> map = multipartFormDataInput.getFormDataMap();
			
			Build build = null;
			
			List<InputPart> lstBuild = map.get("build");
			for (InputPart inputPart : lstBuild) {
				build = inputPart.getBody(Build.class, null);
			}
			
			this.insert(build);
			
			List<InputPart> lstInputPart = map.get("uploadedFile");
			for (InputPart inputPart : lstInputPart) {
				
	        	InputStream inputStream = inputPart.getBody(InputStream.class, null);
				PreparedStatement ps = this.getSessao().getConnection().prepareStatement("INSERT INTO upload (buildid, bytes) VALUES (?, ?)");
				ps.setLong(1, build.getId());
				ps.setBinaryStream(2, inputStream);
				ps.executeUpdate();
				ps.close();
				
			}
			
			String sql = " select length(u.bytes) from Upload u where u.buildId = :buildId ";
			TypedQuery<Integer> query = this.getSessao().getEm().createQuery(sql, Integer.class);
			query.setParameter("buildId", build.getId());
			Integer size = query.getSingleResult();
			
			build.setSize(size);
			this.getSessao().getEm().merge(build);
			
			this.getSessao().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException(e);
		}
		
		return Response.ok().build();
	}
	
	@POST
	@Path("{id}/upload")
	@Consumes(MediaType.APPLICATION_JSON)
	@PermissionMethod(types=UPLOAD, description="Upload de Arquivo")
	public Response upload(byte[] bytes, @PathParam("id") Long buildId) throws RestException {
		
		try {
			
			Build build = this.get(buildId);
			
			Upload upload = new Upload();
			upload.setBuildId(build.getId());
			upload.setBuild(build);
			upload.setBytes(bytes);
			
			build.setSize(bytes.length);
			
			this.getSessao().getEm().persist(upload);
			this.getSessao().getEm().merge(build);
			this.getSessao().commit();
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
		return Response.ok().build();
		
	}
	
	@POST
	@Path("{id}/upload")
	@Consumes(MediaType.APPLICATION_JSON)
	@PermissionMethod(types=UPLOAD, description="Upload de Arquivo")
	public Response upload(Upload upload, @PathParam("id") Long buildId) throws RestException {
		
		return this.upload(upload.getBytes(), buildId);
		
	}
	
	
	@GET
	@Path("{id}/hash")
	@Produces(MediaType.APPLICATION_JSON)
	@PermissionMethod(types=GERAHASH, description="Gera hash para Download externo")
	public BuildHash hashForDownload(@PathParam("id") Long buildId) throws RestException {
		
		try {
			
			Crypto crypto = new Crypto();
			
			BuildHash buildHash =  new BuildHash();
			Build build = this.getSessao().getEm().find(Build.class, buildId);
			buildHash.setBuild(build);
			buildHash.setBuildId(build.getId());
			buildHash.setCreation(LocalDateTime.now());
			buildHash.setStatus(BuildHashStatus.ACTIVE);
			buildHash.setHash(crypto.criptografar(build.getId().toString())+crypto.criptografar(buildHash.getCreation().toString()));
			
			this.getEm().persist(buildHash);
			this.getSessao().commit();
			
			return buildHash;
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
		
	}
	
	@GET
	@Path("{id}/hash/download/{hash}/{nome}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@PermissionMethod(types=ALL, description="Download do Componente pelo hash com nome")
	public  byte[] downloadByhashAndName(@PathParam("id") Long buildId, @PathParam("hash") String hash, @PathParam("nome") String nome) throws RestException {
		
		try {
			
			String sql = " select bh from BuildHash bh where bh.buildId = :buildId and bh.hash = :hash and bh.status = :status";
			TypedQuery<BuildHash> query = getEm().createQuery(sql, BuildHash.class);
			query.setParameter("buildId", buildId);
			query.setParameter("hash", hash);
			query.setParameter("status", BuildHashStatus.ACTIVE);
			BuildHash buildHash = query.getSingleResult();
			
			if (buildHash!=null) {
				buildHash.setStatus(BuildHashStatus.INACTIVE);
				this.getEm().merge(buildHash);
				this.getSessao().commit();
			} else {
				throw new Exception("Hash não encontrado");
			}
			
		} catch (NoResultException e) {
			throw new RestException("Hash para download não encontrado ou encerrado");
		} catch (Exception e) {
			throw new RestException(e);
		}
		return this.download(buildId, nome);
		
	}
	
	@GET
	@Path("{id}/hash/download/{hash}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@PermissionMethod(types=ALL, description="Download do Componente pelo hash")
	public byte[] downloadByhash(@PathParam("id") Long buildId, @PathParam("hash") String hash, @Context HttpServletResponse response) throws RestException {
		
		try {
			
			
			String sql = " select bh from BuildHash bh where bh.buildId = :buildId and bh.hash = :hash and bh.status = :status";
			TypedQuery<BuildHash> query = getEm().createQuery(sql, BuildHash.class);
			query.setParameter("buildId", buildId);
			query.setParameter("hash", hash);
			query.setParameter("status", BuildHashStatus.ACTIVE);
			BuildHash buildHash = query.getSingleResult();
			
			if (buildHash!=null) {
				buildHash.setStatus(BuildHashStatus.INACTIVE);
				this.getEm().merge(buildHash);
				
				String fileName = buildHash.getBuild().getVersion().getComponent().getFileName();
				response.addHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
				
				this.getSessao().commit();
			} else {
				throw new Exception("Hash não encontrado");
			}
			
		} catch (NoResultException e) {
			throw new RestException("Hash para download não encontrado ou encerrado");
		} catch (Exception e) {
			throw new RestException(e);
		}
		return this.download(buildId, null);
		
	}
	

	@GET
	@Path("{id}/download")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@PermissionMethod(types=DOWNLOAD, description="Download do Componente")
	public byte[] download(@PathParam("id") Long buildId) throws RestException {
		return this.download(buildId, null);
	}
	
	@GET
	@Path("{id}/download/{nome}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@PermissionMethod(types=DOWNLOAD, description="Download do Componente com nome")
	public byte[] download(@PathParam("id") Long buildId, @PathParam("nome") String nome) throws RestException {
		
		try {
			
			Upload upload = this.getSessao().getEm().find(Upload.class, buildId);
			return upload.getBytes();
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}

	public Long getComponentId() {
		return componentId;
	}

	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}

	public Long getVersionId() {
		return versionId;
	}

	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}
	
}
