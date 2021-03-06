/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.guvnor.projecteditor.client.forms;

import com.google.gwt.user.client.ui.Widget;
import org.kie.guvnor.project.model.AssertBehaviorOption;
import org.kie.guvnor.project.model.EventProcessingOption;
import org.kie.guvnor.project.model.KBaseModel;
import org.kie.guvnor.projecteditor.client.widgets.Form;

import javax.inject.Inject;

public class KBaseForm
        implements Form<KBaseModel>, KBaseFormView.Presenter {

    private final KBaseFormView view;
    private KBaseModel model;

    @Inject
    public KBaseForm(KBaseFormView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void setModel(KBaseModel knowledgeBaseConfiguration) {

        this.model = knowledgeBaseConfiguration;

        view.setName(knowledgeBaseConfiguration.getName());

        setEqualsBehaviour(knowledgeBaseConfiguration);

        setEventProcessingMode(knowledgeBaseConfiguration);

        setSessions(knowledgeBaseConfiguration);
    }

    @Override
    public void makeReadOnly() {
        view.setReadOnly();
    }

    private void setSessions(KBaseModel knowledgeBaseConfiguration) {
        view.setStatefulSessions(knowledgeBaseConfiguration.getStatefulSessions());
        view.setStatelessSessions(knowledgeBaseConfiguration.getStatelessSessions());
    }

    private void setEventProcessingMode(KBaseModel knowledgeBaseConfiguration) {
        switch (knowledgeBaseConfiguration.getEventProcessingMode()) {
            case CLOUD:
                view.setEventProcessingModeCloud();
                break;

            case STREAM:
                view.setEventProcessingModeStream();
                break;
        }
    }

    private void setEqualsBehaviour(KBaseModel knowledgeBaseConfiguration) {
        switch (knowledgeBaseConfiguration.getEqualsBehavior()) {
            case EQUALITY:
                view.setEqualsBehaviorEquality();
                break;

            case IDENTITY:
                view.setEqualsBehaviorIdentity();
                break;
        }
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    @Override
    public void onEqualsBehaviorEqualitySelect() {
        model.setEqualsBehavior( AssertBehaviorOption.EQUALITY);
    }

    @Override
    public void onEqualsBehaviorIdentitySelect() {
        model.setEqualsBehavior(AssertBehaviorOption.IDENTITY);
    }

    @Override
    public void onEventProcessingModeStreamSelect() {
        model.setEventProcessingMode( EventProcessingOption.STREAM);
    }

    @Override
    public void onEventProcessingModeCloudSelect() {
        model.setEventProcessingMode(EventProcessingOption.CLOUD);
    }
}
