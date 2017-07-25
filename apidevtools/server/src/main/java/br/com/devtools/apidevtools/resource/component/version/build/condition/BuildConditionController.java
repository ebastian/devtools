package br.com.devtools.apidevtools.resource.component.version.build.condition;

import static br.com.devtools.apidevtools.resource.component.version.build.condition.BuildConditionStatus.APPROVED;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.resource.component.version.build.Build;
import br.com.devtools.apidevtools.resource.component.version.build.BuildStatus;

@Path("component/{componentId}/version/{versionId}/build/{buildId}/condition")
public class BuildConditionController extends Controller<BuildCondition> {

	@PathParam("componentId")
	private Long componentId;
	
	@PathParam("versionId")
	private Long versionId;
	
	@PathParam("buildId")
	private Long buildId;

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

	public Long getBuildId() {
		return buildId;
	}

	public void setBuildId(Long buildId) {
		this.buildId = buildId;
	}
	
	private Build getBuild() {
		return this.getEm().find(Build.class, this.getBuildId());
	}
	
	@Override
	public Class<BuildCondition> getClasse() {
		return BuildCondition.class;
	}
	
	@Override
	protected void beforePost(BuildCondition model) throws RestException {
		model.setBuild(this.getBuild());
		model.setStatus(BuildConditionStatus.UNRATED);
	}
	
	@Override
	protected void beforePut(BuildCondition model) throws RestException {
		model.setBuild(this.getBuild());
	}
	
	@Override
	protected BuildCondition afterPost(BuildCondition model) throws RestException {
		this.atualizarBuild();
		return super.afterPost(model);
	}
	
	@Override
	protected BuildCondition afterPut(BuildCondition model) throws RestException {
		this.atualizarBuild();
		return super.afterPut(model);
	}
	
	@Override
	protected BuildCondition afterRemove(BuildCondition model) throws RestException {
		this.atualizarBuild();
		return super.afterRemove(model);
	}
	
	private void atualizarBuild() throws RestException {
		
		Build build = this.getBuild();
		
		try {
			
			String sql = "select bc from BuildCondition bc where bc.build = :build";
			TypedQuery<BuildCondition> query = getEm().createQuery(sql, BuildCondition.class);
			query.setParameter("build", build);
			List<BuildCondition> resultList = query.getResultList();
			
			BuildStatus status = BuildStatus.APPROVED;
			for (BuildCondition condition : resultList) {
				if (!APPROVED.equals(condition.getStatus())) {
					status = BuildStatus.DISAPPROVED;
					break;
				}
			}
			
			if (!status.equals(build.getStatus())){
				build.setStatus(status);
				this.getEm().merge(build);
				this.getSessao().commit();
			}
			
		} catch (NoResultException e) {
			try {
				
				build.setStatus(BuildStatus.APPROVED);
				this.getEm().merge(build);
				this.getSessao().commit();
				
			} catch (Exception e2) {
				throw new RestException(e2);
			}
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}

	@PUT
	@Path("{id}/approve")
	public BuildCondition approve(@PathParam("id") Long id) throws Exception {
		try {
			BuildCondition bc = this.get(id);
			bc.setStatus(BuildConditionStatus.APPROVED);
			return this.put(id, bc);
		} catch (Exception e) {
			throw new RestException(e);
		}
	}
	
	@PUT
	@Path("{id}/disapprove")
	public BuildCondition disapprove(@PathParam("id") Long id) throws Exception {
		try {
			BuildCondition bc = this.get(id);
			bc.setStatus(BuildConditionStatus.DISAPPROVED);
			return this.put(id, bc);
		} catch (Exception e) {
			throw new RestException(e);
		}
	}
	
	
}
