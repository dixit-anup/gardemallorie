// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.appspot.gardemallorie.domain;

import com.appspot.gardemallorie.domain.Garde;
import com.appspot.gardemallorie.domain.GardeDataOnDemand;
import com.appspot.gardemallorie.domain.GardeIntegrationTest;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import java.util.Iterator;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

privileged aspect GardeIntegrationTest_Roo_IntegrationTest {
    
    declare @type: GardeIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: GardeIntegrationTest: @ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml");
    
    declare @type: GardeIntegrationTest: @Transactional;
    
    @Autowired
    GardeDataOnDemand GardeIntegrationTest.dod;
    
    private static final LocalServiceTestHelper GardeIntegrationTest.helper = new LocalServiceTestHelper(new com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig());
    
    @BeforeClass
    public static void GardeIntegrationTest.setUp() {
        helper.setUp();
    }
    
    @AfterClass
    public static void GardeIntegrationTest.tearDown() {
        helper.tearDown();
    }
    
    @Test
    public void GardeIntegrationTest.testCountGardes() {
        Assert.assertNotNull("Data on demand for 'Garde' failed to initialize correctly", dod.getRandomGarde());
        long count = Garde.countGardes();
        Assert.assertTrue("Counter for 'Garde' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void GardeIntegrationTest.testFindGarde() {
        Garde obj = dod.getRandomGarde();
        Assert.assertNotNull("Data on demand for 'Garde' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Garde' failed to provide an identifier", id);
        obj = Garde.findGarde(id);
        Assert.assertNotNull("Find method for 'Garde' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Garde' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void GardeIntegrationTest.testFindAllGardes() {
        Assert.assertNotNull("Data on demand for 'Garde' failed to initialize correctly", dod.getRandomGarde());
        long count = Garde.countGardes();
        Assert.assertTrue("Too expensive to perform a find all test for 'Garde', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Garde> result = Garde.findAllGardes();
        Assert.assertNotNull("Find all method for 'Garde' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Garde' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void GardeIntegrationTest.testFindGardeEntries() {
        Assert.assertNotNull("Data on demand for 'Garde' failed to initialize correctly", dod.getRandomGarde());
        long count = Garde.countGardes();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Garde> result = Garde.findGardeEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Garde' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Garde' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void GardeIntegrationTest.testFlush() {
        Garde obj = dod.getRandomGarde();
        Assert.assertNotNull("Data on demand for 'Garde' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Garde' failed to provide an identifier", id);
        obj = Garde.findGarde(id);
        Assert.assertNotNull("Find method for 'Garde' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyGarde(obj);
        Long currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Garde' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void GardeIntegrationTest.testMergeUpdate() {
        Garde obj = dod.getRandomGarde();
        Assert.assertNotNull("Data on demand for 'Garde' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Garde' failed to provide an identifier", id);
        obj = Garde.findGarde(id);
        boolean modified =  dod.modifyGarde(obj);
        Long currentVersion = obj.getVersion();
        Garde merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Garde' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void GardeIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Garde' failed to initialize correctly", dod.getRandomGarde());
        Garde obj = dod.getNewTransientGarde(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Garde' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Garde' identifier to be null", obj.getId());
        try {
            obj.persist();
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        obj.flush();
        Assert.assertNotNull("Expected 'Garde' identifier to no longer be null", obj.getId());
    }
    
    @Test
    @Transactional(propagation = Propagation.SUPPORTS)
    public void GardeIntegrationTest.testRemove() {
        Garde obj = dod.getRandomGarde();
        Assert.assertNotNull("Data on demand for 'Garde' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Garde' failed to provide an identifier", id);
        obj = Garde.findGarde(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Garde' with identifier '" + id + "'", Garde.findGarde(id));
    }
    
}
