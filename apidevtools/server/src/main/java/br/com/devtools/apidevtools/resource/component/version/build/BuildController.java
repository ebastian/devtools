package br.com.devtools.apidevtools.resource.component.version.build;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.controller.Filter;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.resource.component.version.Version;
import br.com.devtools.apidevtools.resource.component.version.VersionController;

@Path("component/{componentId}/version/{versionId}/build")
public class BuildController extends Controller<Build> {

	@PathParam("componentId") Long componentId;
	
	@PathParam("versionId") Long versionId;

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
			versionController.setComponentId(this.componentId);
			this.version = versionController.get(this.versionId);

			if (this.version == null) {
				throw new RestException("Version com código " + this.versionId + " não encontrado.");
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
	public Response upload(MultipartFormDataInput multipartFormDataInput, @PathParam("id") Long buildId) throws RestException {
		
		try {
			
			Map<String, List<InputPart>> map = multipartFormDataInput.getFormDataMap();
			List<InputPart> lstInputPart = map.get("uploadedFile");
			
			for (InputPart inputPart : lstInputPart) {
				
	        	InputStream inputStream = inputPart.getBody(InputStream.class, null);
				PreparedStatement ps = this.getSessao().getConnection().prepareStatement("INSERT INTO upload (buildid, bytes) VALUES (?, ?)");
				ps.setLong(1, buildId);
				ps.setBinaryStream(2, inputStream);
				ps.executeUpdate();
				ps.close();
				
			}
			
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
	public Response upload(byte[] bytes, @PathParam("id") Long buildId) throws RestException {
		
		try {
			
			Build build = this.get(buildId);
			
			Upload upload = new Upload();
			upload.setBuild(build);
			upload.setBytes(bytes);
			
			this.getSessao().getEm().persist(upload);
			this.getSessao().commit();
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
		return Response.ok().build();
		
	}
	
	@GET
	@Path("{id}/download")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public byte[] download(@PathParam("id") Long buildId) throws RestException {
		
		try {
			
			Upload upload = this.getSessao().getEm().find(Upload.class, buildId);
			return upload.getBytes();
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}
	
	@GET
	@Path("{id}/download/{nome}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public byte[] downloadComNome(@PathParam("id") Long buildId, @PathParam("nome") String nome) throws RestException {
		
		try {
			
			Upload upload = this.getSessao().getEm().find(Upload.class, buildId);
			return upload.getBytes();
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}
	
}
