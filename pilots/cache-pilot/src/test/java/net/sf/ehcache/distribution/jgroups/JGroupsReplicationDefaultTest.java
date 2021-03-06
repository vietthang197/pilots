/**
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * @author Pierre Monestie (pmonestie__REMOVE__THIS__@gmail.com)
 * @author <a href="mailto:gluck@gregluck.com">Greg Luck</a>
 * @version $Id: JGroupsReplicationTest.java 2931 2010-10-14 02:09:50Z gluck $
 */

package net.sf.ehcache.distribution.jgroups;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.util.ClassLoaderUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;

import static org.junit.Assert.*;


/**
 * Test JGroups replication
 *
 * @author <a href="mailto:gluck@gregluck.com">Greg Luck</a>
 */
public class JGroupsReplicationDefaultTest {

    private CacheManager manager1;
    private CacheManager manager2;

    private String cacheName;
    private static final String SAMPLE_CACHE1 = "sampleCacheAsync";
    private static final int NBR_ELEMENTS = 100;

    @Before
    public void setUp() throws Exception {

        manager1 = new CacheManager(ClassLoaderUtil.getStandardClassLoader().getResource("jgroups/ehcache-distributed-jgroups-default.xml"));
        manager2 = new CacheManager(ClassLoaderUtil.getStandardClassLoader().getResource("jgroups/ehcache-distributed-jgroups-default.xml"));
        cacheName = SAMPLE_CACHE1;
    }

    @After
    public void tearDown() throws Exception {
        manager1.shutdown();
        manager2.shutdown();
    }

    @Test
    public void run() throws Exception {


        final Ehcache cache1 = manager1.getEhcache(cacheName);
        final Ehcache cache2 = manager2.getEhcache(cacheName);

        for (int i = 0; i < NBR_ELEMENTS; i++) {
            cache1.put(new Element(i, "testdat"));
        }

       Thread.sleep(2000);

        assertEquals(NBR_ELEMENTS, cache1.getKeys().size());
        assertEquals(NBR_ELEMENTS, cache2.getKeys().size());

        System.out.println("######## SUCCESS!!");
    }

    public static void main(String[] args) throws Exception {

        JGroupsReplicationDefaultTest test = new JGroupsReplicationDefaultTest();
        test.setUp();
        test.run();
        test.tearDown();

    }


}
