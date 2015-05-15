package cg.group4;
import java.util.HashMap;
import java.util.Map;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

/**
 * This class is a helper class we found on the internet. This helps so we have Global values from Gdx classes.
 * Without a proper running instance of the LibGDX application a lot of things cannot be tested.
 * @author <a href="https://bitbucket.org/TomGrill>TomGrill</a>
 * @author <a href="http://badlogicgames.com/forum/viewtopic.php?f=17&t=1485">__nocach</a>
 * @see <a href="https://bitbucket.org/TomGrill/libgdx-testing-sample">GdxTestRunner lib</a>
 */
public class GdxTestRunner extends BlockJUnit4ClassRunner implements ApplicationListener{

    private Map<FrameworkMethod, RunNotifier> invokeInRender = new HashMap<FrameworkMethod, RunNotifier>();

    public GdxTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();

        new HeadlessApplication(this, conf);
    }

    @Override
    public void create() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void render() {
        synchronized (invokeInRender) {
            for(Map.Entry<FrameworkMethod, RunNotifier> each : invokeInRender.entrySet()){
                super.runChild(each.getKey(), each.getValue());
            }
            invokeInRender.clear();
        }
    }



    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        synchronized (invokeInRender) {
            //add for invoking in render phase, where gl context is available
            invokeInRender.put(method, notifier);
        }
        //wait until that test was invoked
        waitUntilInvokedInRenderMethod();
    }

    /**
     *
     */
    private void waitUntilInvokedInRenderMethod() {
        try {
            while (true){
                Thread.sleep(10);
                synchronized (invokeInRender) {
                    if (invokeInRender.isEmpty()) break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}