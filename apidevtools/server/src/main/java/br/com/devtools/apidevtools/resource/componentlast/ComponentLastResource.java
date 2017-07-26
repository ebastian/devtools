package br.com.devtools.apidevtools.resource.componentlast;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.devtools.apidevtools.resource.component.Component;
import br.com.devtools.apidevtools.resource.component.version.Version;
import br.com.devtools.apidevtools.resource.component.version.build.Build;

public class ComponentLastResource {

	private EntityManager em;
	
	public ComponentLastResource(EntityManager em) {
		this.em = em;
	}

	private EntityManager getEm() {
		return em;
	}
	
	public ComponentLast last(Long componentId, Long versionId) throws Exception {

		ComponentLast compLast = new ComponentLast();
		
		try {
			
			Component component = this.getEm().find(Component.class, componentId);
			
			if (component!=null) {
				
				compLast.setComponent(component);
				
				String sqlVersion = 
						" select v from Version v "
						+ " where v.component = :component ";
				if (versionId!=null) {
					sqlVersion += " and v.id = :versionId ";
				}
				sqlVersion += 
						" order by v.major desc, v.minor desc, v.release desc ";
				TypedQuery<Version> queryVersion = getEm().createQuery(sqlVersion, Version.class);
				queryVersion.setParameter("component", component);
				if (versionId!=null) {
					queryVersion.setParameter("versionId", versionId);
				}
				queryVersion.setMaxResults(1);
				
				Version version = queryVersion.getSingleResult();
				
				if (version!=null) { 
					
					compLast.setVersion(version);
					
					String sqlBuild = 
							" select b from Build b "
							+ " where b.version = :version "
							+ " order by b.build desc ";
					TypedQuery<Build> queryBuild = getEm().createQuery(sqlBuild, Build.class);
					queryBuild.setParameter("version", version);
					queryBuild.setMaxResults(1);
					
					Build build = queryBuild.getSingleResult();
					
					compLast.setBuild(build);
					
				}
				
			}
			
		} catch (Exception e) {
			//throw new RestException(e);
		}
		
		return compLast;
		
	}
	
	public ComponentLast last(Long componentId) throws Exception {
		return this.last(componentId, null);
	}

}
