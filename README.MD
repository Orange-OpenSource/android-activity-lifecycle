#Lifecycle
If you are not familiar to the lifecycle concept in android activities, please have a look [here](https://developer.android.com/training/basics/activity-lifecycle/index.html]).

When you develop a real world application, with a lot of classes, there is a good chance that you need to do some stuff when activities' lifecycle events occur. For instance to release some resources or unregister from services when the "on destroy" event occurs.

It can lead to write what we call "boiler plate" code, since the event is notified through an [Activity](https://developer.android.com/reference/android/app/Activity.html) callback, and then you will have to forward this event to underlying objects through another mean. But let's give an example:

Your activity, which receives the "on destroy" event:
```java
public class MyActivity extends Activity {
  private MyObject myObject;
  ...
  @Override
  public void onDestroy() {
    super.onDestroy();
    myObject.onDestroy();
  }
}
``` 
Then your object, which needs to release resources when the activity is destroyed:
```java
class MyObject {
  private Runnable runnable;
  private Handler handler;
  ...
  void onDestroy() {
    handler.removeCallbacks(runnable);
  }
}
```

So here there is some room for improvment:
- in our activity, we have to keep the ```myObject``` field only to forward the "on destroy" event to this object
- in our activity, we have to override the ```onDestroy``` function only for this purpose as well
- in our object we rely on ```MyActivity``` to provide the "on destroy" event

It would be great if we could break this strong link between these two classes and that is the purpose of this library.

With the "android activity lifecycle" library, you can write:
```java
public class MyActivity extends Activity {
  // only the activity business!
}
``` 
```java
// implements Lifecycle interface to receive events
class MyObject implements Lifecycle {
  private Runnable runnable;
  private Handler handler;
  
  MyObject(Context context) {
    // Register for activity lifecycle events
    // with the context, which identifies the activity
    ApplicationLifecycle.register(this, context);
  }
  ...
  @Override
  public void onLifecycleEvent(LifecycleEvent lifecycleEvent) {
    if (lifecycleEvent == LifecycleEvent.ON_DESTROY) {
      // my business
      handler.removeCallbacks(runnable);
    }
  }
}
```

So here we can see that:
 - the activity source code is cleaner, and can focus on its own business
 - the object which needs to react to activity lifecyle events can handle its business by its own
 
We have reduce coupling between these objects.

#Build & use
It is a maven build, so basically ```mvn install</code> should do the job. It will install the library in your local repository. You can also retrieve the jar file under the target directory (after the maven build).

The library is published on maven central to ease developers' life. So to use the library in a maven build:
<pre><code>&lt;dependency&gt;
  &lt;groupId&gt;&lt;/groupId&gt;
  &lt;artifactId&gt;&lt;/artifactId&gt;
  &lt;version&gt;&lt;/version&gt;
  &lt;scope&gt;compile&lt;/scope&gt;
&lt;/dependency&gt;
</pre></code>

In a gradle build:
<pre><code>dependencies {
  compile "::"
}
</pre></code>

#License
Apache License Version 2.0
#Authors
Christophe Maldivi & Stephane Coutant

