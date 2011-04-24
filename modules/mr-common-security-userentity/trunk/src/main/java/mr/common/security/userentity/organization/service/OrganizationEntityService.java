package mr.common.security.userentity.organization.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import mr.common.model.ConfigurableData;
import mr.common.security.organization.exception.InvalidOrganizationNameException;
import mr.common.security.organization.exception.OrganizationNotExistException;
import mr.common.security.organization.model.Organization;
import mr.common.security.organization.service.OrganizationService;
import mr.common.security.userentity.organization.dao.OrganizationEntityDao;
import mr.common.security.userentity.organization.model.OrganizationEntity;

import org.springframework.transaction.annotation.Transactional;


/**
 * Implementación de {@link mr.common.security.organization.
 * service.OrganizationService OrganizationService}.
 *
 * @see mr.common.security.userentity.organization.model.OrganizationEntity
 * @author Mariano Ruiz
 */
public class OrganizationEntityService implements OrganizationService {

	@Resource
	private OrganizationEntityDao orgDao;


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true)
	public List getList() {
		return orgDao.getList();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true)
	public List find(String nameOrDescription, Boolean activeFilter,
			ConfigurableData page) {
		return orgDao.find(nameOrDescription, activeFilter, page);
	}

	@Transactional(readOnly = true)
	public int findCount(String nameOrDescription, Boolean activeFilter) {
		return orgDao.findCount(nameOrDescription, activeFilter);
	}

	@Transactional(readOnly = true)
	public Organization getById(Serializable id) {
    	if(id==null) {
    		throw new NullPointerException("id = null");
    	}
    	Organization org = orgDao.get((Long)id);
		if(org==null) {
			throw new OrganizationNotExistException(
					"Organization with id=" + id + " not exist");
		}
		return org;
	}

	@Transactional(readOnly = true)
	public Organization getByName(String name) {
    	if(name==null) {
    		throw new NullPointerException("name = null");
    	}
    	Organization org = orgDao.getByName(name);
		if(org==null) {
			throw new OrganizationNotExistException(
					"Organization with name=" + name + " not exist");
		}
		return org;
	}

	@Transactional(readOnly = false)
	public Organization newOrganization(Organization org) {
		if(org.getId()!=null) {
			throw new IllegalArgumentException(
					"New organization should not have set the id.");
		}
		return saveOrUpdate((OrganizationEntity)org);
	}

	@Transactional(readOnly = false)
	private Organization saveOrUpdate(OrganizationEntity org) {
		OrganizationEntity organization;
		if(org.getId()!=null) {
			organization = (OrganizationEntity) getById(org.getId());
		} else {
			organization = new OrganizationEntity();
		}
		if(isValidOrganizationName(org.getName())) {
			organization.setName(org.getName());
		} else {
			throw new InvalidOrganizationNameException(org.getName());
		}
		organization.setDescription(org.getDescription());
		organization.setCountryId(org.getCountryId());
		organization.setCityOrRegionName(org.getCityOrRegionName());
		organization.setStateOrProvinceName(org.getStateOrProvinceName());
		organization.setPostalAddress(org.getPostalAddress());
		organization.setPostalCode(org.getPostalCode());
		organization.setTelephoneNumber(org.getTelephoneNumber());
		return orgDao.merge(organization);
	}

	@Transactional(readOnly = false)
	public Organization updateOrganization(Serializable id, Organization organization) {
		if(id==null) {
			throw new NullPointerException(
					"id = null.");
		}
		if(organization.getId()!=null) {
			throw new IllegalArgumentException(
					"Argument `organization` should not have set the id.");
		}
		OrganizationEntity organizationEntity = (OrganizationEntity) organization;
		organizationEntity.setId((Long)id);
		return saveOrUpdate((OrganizationEntity)organization);
	}

	@Transactional(readOnly = false)
	public Organization updateOrganization(String name, Organization organization) {
		if(name==null) {
			throw new NullPointerException(
					"name = null.");
		}
		if(organization.getId()!=null) {
			throw new IllegalArgumentException(
					"Argument `organization` should not have set the id.");
		}
		OrganizationEntity organizationEntity = (OrganizationEntity) organization;
		organizationEntity.setId(getByName(name).getId());
		return saveOrUpdate((OrganizationEntity)organization);
	}

	public Organization getOrganizationInstance() {
		return new OrganizationEntity();
	}

	public boolean isValidOrganizationName(String name) {
		if(!name.equals(name.trim())) {
			return false;
		}
		return true;
	}

	@Transactional(readOnly = false)
	public void deleteByName(String name) {
		orgDao.delete((OrganizationEntity) getByName(name));
	}

	@Transactional(readOnly = false)
	public void deleteById(Serializable id) {
		orgDao.delete((OrganizationEntity) getById(id));
	}
}
