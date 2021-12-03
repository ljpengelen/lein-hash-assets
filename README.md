# lein-hash-assets

A Leiningen plugin that
1. adds md5 hashes to the filenames of your static assets and
1. uses these filenames in your `index.html`.

This allows you to permit caching of your static assets, without the risk of users ever seeing outdated versions due to caching.
Do not allow caching of `index.html` itself, however.

## Usage

First, put `[lein-hash-assets "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your project.clj and provide a configuration of the following form:

```
:hash-assets {:source-root "resources/public"
              :target-root "dist"
              :index "index.html"
              :files ["css/screen.css" "js/compiled/app.js"]}
```

Then, execute the following command:

    $ lein hash-assets

This will calculate the md5 hashes of `resources/public/css/screen.css` and `resources/public/js/compiled/app.js`, and place copies of these files with the corresponding hashes in their filename into `dist/css/screen-<hash>.css` and `dist/js/compiled/app-<hash>.js`.
Additionally, it will copy `resources/public/index.html` into `dist/index.html`, with all references to the original files replaced by their renamed counterparts.

## License

MIT
