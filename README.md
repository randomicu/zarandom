# zarandom
Slack bot to get a random item from list

## Install Caddy server on Centos

```
yum install yum-plugin-copr
yum copr enable @caddy/caddy
yum install caddy
```

Run and enable Caddy:

```
systemctl start caddy
systemctl enable caddy
systemctl status caddy
```

## Deploy with Caddy and Docker

Run docker container: `docker run --name slack-zarandom --publish 127.0.0.1:8111:8080 --detach ghcr.io/randomicu/zarandom:0.0.2`

Configure Caddy, paste this to `/etc/caddy/Caddyfile`:

```
zarandom.slack-apps.random.icu {

    root * /usr/share/caddy
    
    file_server
    
    # Proxy /api/ endpoint to java app
    # reverse_proxy /api/* localhost:8111

    route /api/* {
        uri strip_prefix /api
        reverse_proxy localhost:8111
    }

    # Logging
    log {
        output file /var/log/caddy/zarandom-access.log
        #format single_field common_log
        format json
        level info
    }
}
```
