/**
 * Copyright (C) 2012 Ness Computing, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nesscomputing.db.postgres.embedded;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;
import org.kitei.testing.lessio.AllowExternalProcess;
import org.kitei.testing.lessio.AllowTmpDirAccess;
import org.kitei.testing.lessio.AllowNetworkAccess;
import org.kitei.testing.lessio.AllowNetworkListen;

@AllowExternalProcess
@AllowTmpDirAccess
@AllowNetworkListen(ports = {0})
@AllowNetworkAccess(endpoints = {"127.0.0.1:*"})
public class EmbeddedPostgreSQLTest
{
    @Test
    public void testEmbeddedPg() throws Exception
    {
        EmbeddedPostgreSQL pg = EmbeddedPostgreSQL.start();

        try {
            Connection c = pg.getPostgresDatabase().getConnection();
            try {
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT 1");
                assertTrue(rs.next());
                assertEquals(1, rs.getInt(1));
                assertFalse(rs.next());
            } finally {
                c.close();
            }
        } finally {
            pg.close();
        }
    }
}
