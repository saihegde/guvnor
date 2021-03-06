package org.kie.guvnor.commons.ui.client.menu;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.Caller;
import org.kie.guvnor.commons.ui.client.callbacks.HasBusyIndicatorDefaultErrorCallback;
import org.kie.guvnor.commons.ui.client.popups.file.CommandWithCommitMessage;
import org.kie.guvnor.commons.ui.client.popups.file.SaveOperationService;
import org.kie.guvnor.commons.ui.client.resources.i18n.CommonConstants;
import org.kie.guvnor.commons.ui.client.widget.BusyIndicatorView;
import org.kie.guvnor.services.version.VersionService;
import org.kie.guvnor.services.version.events.RestoreEvent;
import org.uberfire.backend.vfs.Path;
import org.uberfire.client.mvp.Command;

public class RestoreVersionCommandProvider {

    @Inject
    private Caller<VersionService> versionService;

    @Inject
    private Event<RestoreEvent> restoreEvent;

    @Inject
    private BusyIndicatorView busyIndicatorView;

    Command getCommand( final Path path ) {
        return new Command() {
            @Override
            public void execute() {
                new SaveOperationService().save( path,
                                                 new CommandWithCommitMessage() {
                                                     @Override
                                                     public void execute( final String comment ) {
                                                         busyIndicatorView.showBusyIndicator( CommonConstants.INSTANCE.Restoring() );
                                                         versionService.call( getRestorationSuccessCallback(),
                                                                              new HasBusyIndicatorDefaultErrorCallback( busyIndicatorView ) ).restore( path, comment );
                                                     }
                                                 } );
            }
        };
    }

    private RemoteCallback<Path> getRestorationSuccessCallback() {
        return new RemoteCallback<Path>() {
            @Override
            public void callback( final Path restored ) {
                //TODO {porcelli} close current?
                busyIndicatorView.hideBusyIndicator();
                restoreEvent.fire( new RestoreEvent( restored ) );
            }
        };
    }

}
