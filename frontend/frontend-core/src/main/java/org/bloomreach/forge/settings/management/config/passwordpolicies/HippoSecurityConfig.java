/*
 * Copyright 2013-2020 Bloomreach Inc. (http://www.bloomreach.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bloomreach.forge.settings.management.config.passwordpolicies;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.hippoecm.repository.api.HippoNodeType;
import org.bloomreach.forge.settings.management.config.CMSFeatureConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Config mapping for path
 */
public class HippoSecurityConfig implements CMSFeatureConfig {

    private final static Logger log = LoggerFactory.getLogger(HippoSecurityConfig.class);
    private String maxAgeForPasswords;
    private transient Node configNode;

    public HippoSecurityConfig(final Node node) {
        init(node);
    }

    private void init(final Node node) {
        this.configNode = node;
        try {
            if (node.hasProperty(HippoNodeType.HIPPO_PASSWORDMAXAGEDAYS)) {
                maxAgeForPasswords = node.getProperty(HippoNodeType.HIPPO_PASSWORDMAXAGEDAYS).getString();
            }
        } catch (RepositoryException e) {
            log.error("Error: {}", e);
        }
    }

    public String getMaxAgeForPasswords() {
        return maxAgeForPasswords;
    }

    public void setMaxAgeForPasswords(final String maxAgeForPasswords) {
        this.maxAgeForPasswords = maxAgeForPasswords;
    }

    @Override
    public void save() throws RepositoryException {
        if(maxAgeForPasswords!=null) {
            configNode.setProperty(HippoNodeType.HIPPO_PASSWORDMAXAGEDAYS, maxAgeForPasswords);
        }
        configNode.getSession().save();
    }
}
