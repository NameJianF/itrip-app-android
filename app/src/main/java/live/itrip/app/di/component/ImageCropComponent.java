package live.itrip.app.di.component;

import dagger.Component;
import live.itrip.app.di.PerActivity;
import live.itrip.app.di.module.ActivityModule;

/**
 * Created by Feng on 2017/6/21.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ImageCropComponent {
}
