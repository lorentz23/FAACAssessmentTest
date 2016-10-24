package com.faac.assessment.test.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/*
 *  Generic DAO that contains all the shared method to retrieve model
 */
public abstract class AbstractDao<T extends Object> {
	
	protected static final Logger logger = LogManager.getLogger(AbstractDao.class);
	
	protected final Class<T> type;
	
	@PersistenceContext(name = "faac-assessment-test")
	protected EntityManager em;
	
	public AbstractDao(Class<T> type) {
		this.type = type;
	}

	/**
     * Search an entity by id
     * If id is null then null is returned
     *
     * @param id an entity ID
     * @return the entity with the given id or null if no such entity exists
     */
    public T findById(Id  id) {
        
    	if (id == null) {
            return null;
        }
        
        T result = null;
        
        try {
        	result = em.find(type, id);
        } catch(EntityNotFoundException e) {
        	logger.error("Unable to find entity by " + id + " " + e.getMessage());
        }
        
        return result;
    }
    
    public List<T> findAll() {
    	
    	try {
    		return em.createNamedQuery(getFindAllNamedQuery(), type).getResultList();
    	} catch(Exception e) {
    		return null;
    	}
    }
    
    @Transactional
    public void removeUser(T entity) {
    	try {
    		em.getTransaction().begin();
    		em.remove(entity);
    		em.getTransaction().commit();
    	} catch(Exception e) {
    		logger.error("Unable to remove entity " + e.getMessage());
    	}
    }
    
    public abstract String getFindAllNamedQuery();
}
