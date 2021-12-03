# lein-hash-assets

A Leiningen plugin that
1. adds md5 hashes to the filenames of your static assets and
1. uses these filenames in your `index.html`.

This allows you to permit caching of your static assets, without the risk of users ever seeing outdated versions due to caching.
Do not allow caching of `index.html` itself, however.

## Usage

Put `[lein-hash-assets "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your project.clj.

FIXME: and add an example usage that actually makes sense:

    $ lein hash-assets

## License

MIT
