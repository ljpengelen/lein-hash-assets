# lein-hash-assets

A Leiningen plugin that
1. adds md5 hashes to the filenames of your static assets and
1. uses these filenames in your `index.html`.

This allows you to permit caching of your static assets, without the risk of users ever seeing outdated versions due to caching.
Do not allow caching of `index.html` itself, however.

## Usage

First, put `[com.github.ljpengelen/lein-hash-assets "x.y.z"]` into the `:plugins` vector of your `project.clj`.
The latest version is shown in the badge below.

[![Clojars Project](https://img.shields.io/clojars/v/com.github.ljpengelen/lein-hash-assets.svg)](https://clojars.org/com.github.ljpengelen/lein-hash-assets)

Second, add a configuration of the following form to your `project.clj`:

```
:hash-assets {:source-root "resources/public"
              :target-root "dist"
              :index "index.html"
              :files ["css/screen.css" "js/compiled/app.js"]}
```

Then, execute the following command:

    $ lein hash-assets

For the given configuratio, this will calculate the md5 hashes of `resources/public/css/screen.css` and `resources/public/js/compiled/app.js`, and place copies of these files with the corresponding hashes in their filename into `dist/css/screen-<hash>.css` and `dist/js/compiled/app-<hash>.js`.
Additionally, it will copy `resources/public/index.html` into `dist/index.html`, with all references to the original files replaced by their renamed counterparts.

## License

MIT
