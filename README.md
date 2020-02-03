# Easy, scalable backends for Android developers

This is the sample project to accompany my conference talk "Easy, scalable
backends for Android developers". There are two primary use cases illustrated
here, with both frontend and backend code provided. The frontend is an Android
app that uses Kotlin, coroutines, Jetpack architecture components, and MVVM. The
backend is hosted on Firebase / Google Cloud.

The top-level components in this repo are:

- An Android app under [android](android)
- A [Cloud Functions][13] backend under [cloud-functions](cloud-functions)
- A [Cloud Run][14] backend under [cloud-run](cloud-run)

The information and instructions here require that you're familiar with these
processes:

1. Creating a Firebase / Google Cloud project and adding an Android app to it
   ([documentation][1])
1. Deploying code to Cloud Functions using the [Firebase CLI][12]
1. Deploying code to Cloud Run using the Google Cloud SDK (gcloud)

Please note that a Firebase project **is** a Google Cloud project. If you create
a Firebase project, you are also implicitly creating a Google Cloud project, and
you have all the features and benefits of Google Cloud available in your
Firebase project. To better understand the relationship between Firebase and
Google Cloud, [read this blog][15].

## Cloud Functions: Firebase callable function (TypeScript, nodejs)

This repo contains sample code that shows how an Android app can invoke a
[Firebase callable function][11] using the provided SDKs.

- Cloud Functions / TypeScript code in [index.ts][3] under
  [cloud-functions](cloud-functions).
- Android / Kotlin code contained in a [package for a fragment][2].

Note that it's possible to deploy and run this code on the Firebase free Spark
plan.

To run this sample:

1. Create a Firebase project and add the Android app in this repo to it.
1. Deploy the Cloud Functions code using the [Firebase CLI][4].
1. Launch the Android app and press the "invoke callable sum" button.

Deployment with the Firebase CLI goes like this:

```sh
cd cloud-functions
firebase deploy
```

The output should show that the function called "sum" was successfully deployed.

In the Android app, when you ress the "invoke callable sum" button, it will
use the Firebase Functions SDK to invoke the function and get a sum of two
integers, as defined in the client source code.

## Cloud Run: HTTP endpoints (Kotlin, coroutines, ktor, kotlinx.serialization)

This repo contains sample code that shows how an Android app can invoke a
backend deployed to Cloud Run using Kotlin and ktor.

- Cloud Run / Kotlin code in [package under cloud-run][6]. It uses a
  Kotlin web server called [ktor](https://ktor.io/).
- Android / Kotlin code contained in a [package for a fragment][5]. It
  uses the ktor HTTP client library.

Your Firebase project must be on a payment plan in order to deploy and run this
sample. For basic experimentation, you are highly unlikely to incur any actual
costs, as there is a perpetual free allowance for all of the Firebase and Google
Cloud products in use here. If you a concerned about future charges, then you
can simply delete the project or disable billing after you're done experimenting
with it.

Summary of steps to run the sample:

1. Create a Firebase / Google Cloud project, or reuse the one from the prior
   sample.
1. Build and deploy the Cloud Run code using gcloud (using the commands in the
   provided deploy.sh script)
1. Note the base URL of the deployment in the output. It will have a domain of
   "run.app".
1. Edit [android/app/src/main/res/values/strings.xml][17] and copy the base URL
   into the `cloud_run_root` string resource.
1. Run the Android app and press the "invoke cloud run endpoint" button.

*Note that it can be time-consuming to get started with Google Cloud if you
don't have an experience with it, and the instructions here are not
comprehensive. You will likely need to spend some time sorting through the
documentation in order to get acquainted with how Google Cloud and its CLI and
console work. You should also study the [Cloud Run documentation][10] to better
understand how to navigate the product and the Google Cloud console.*

You should have the [Google Cloud SDK][7] (particularly, the gcloud CLI)
installed and configured to deploy the backend code to Cloud Run. You will also
need a unique name for your app to put in place of the $APP_NAME variable below
in order to build and deploy a container to Cloud Run:

```sh
cd cloud-run
./deploy.sh $PROJECT_NAME $APP_NAME
```

`$PROJECT_NAME` is the name of your project, and `$APP_NAME` is the name of your
Cloud Run app (also called a "service"). Examine the [deploy.sh][16] shell
script to see the gcloud commands that it runs to build and deploy a docker
image to Cloud Run. If the Cloud Run app you named here doesn't exist, it will
be created. If you are prompted for anything during deployment, take the
defaults.

If you are running an OS that can't run unix shell scripts, you will have to
reverse-engineer the gcloud commands in build.sh for build and deployment.

When the deployment finishes, you will recevie a base URL for your HTTP
endpoints. For example, the last line of output should look like this:

```txt
Service [helloworld] revision [helloworld-00008-sih] has been deployed and is
serving 100 percent of traffic at https://helloworld-jtdztnhypa-uc.a.run.app
```

This root URL needs to be copied to the `CLOUD_RUN_ROOT` constant in the
[CloudRunRepository.kt][8] source file.

[1]: https://firebase.google.com/docs/android/setup
[2]: android/app/src/main/java/com/hyperaware/bfa/android/fragment/callablesum
[3]: cloud-functions/functions/src/index.ts
[4]: https://firebase.google.com/docs/cli
[5]: android/app/src/main/java/com/hyperaware/bfa/android/fragment/cloudrunendpoint
[6]: cloud-run/src/main/kotlin/com/hyperaware/bfa/cloudrun
[7]: https://cloud.google.com/sdk/docs
[8]: android/app/src/main/java/com/hyperaware/bfa/android/fragment/cloudrunendpoint/CloudRunEndpointRepository.kt
[9]: https://cloud.google.com/run/docs/quickstarts/build-and-deploy
[10]: https://cloud.google.com/run/docs/
[11]: https://firebase.google.com/docs/functions/callable
[12]: https://firebase.google.com/docs/cli
[13]: https://firebase.google.com/docs/functions/
[14]: https://cloud.google.com/run
[15]: https://medium.com/google-developers/whats-the-relationship-between-firebase-and-google-cloud-57e268a7ff6f
[16]: cloud-run/deploy.sh
[17]: android/app/src/main/res/values/strings.xml
