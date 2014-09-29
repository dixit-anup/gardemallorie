// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.appspot.gardemallorie.domain;

import com.appspot.gardemallorie.domain.BabySitting;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

privileged aspect BabySitting_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager BabySitting.entityManager;
    
    public static final List<String> BabySitting.fieldNames4OrderClauseFilter = java.util.Arrays.asList("MILLISECONDS_PER_HOURS", "day", "babySitter", "plannedBeginning", "go", "plannedEnd", "back", "comment", "location", "declaredEnd", "chargedEnd", "extraHours", "childcareVouchers", "copyUntil");
    
    public static final EntityManager BabySitting.entityManager() {
        EntityManager em = new BabySitting().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    @Transactional
    public static long BabySitting.countBabySittings() {
        return findAllBabySittings().size();
    }
    
    @Transactional
    public static List<BabySitting> BabySitting.findAllBabySittings() {
        return entityManager().createQuery("SELECT o FROM BabySitting o", BabySitting.class).getResultList();
    }
    
    @Transactional
    public static List<BabySitting> BabySitting.findAllBabySittings(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM BabySitting o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, BabySitting.class).getResultList();
    }
    
    @Transactional
    public static BabySitting BabySitting.findBabySitting(Long id) {
        if (id == null) return null;
        return entityManager().find(BabySitting.class, id);
    }
    
    @Transactional
    public static List<BabySitting> BabySitting.findBabySittingEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM BabySitting o", BabySitting.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public static List<BabySitting> BabySitting.findBabySittingEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM BabySitting o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, BabySitting.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void BabySitting.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void BabySitting.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            BabySitting attached = BabySitting.findBabySitting(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void BabySitting.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void BabySitting.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public BabySitting BabySitting.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        BabySitting merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
