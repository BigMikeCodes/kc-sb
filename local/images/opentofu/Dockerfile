### Build
FROM ghcr.io/opentofu/opentofu:1.12-minimal AS builder

### Runtime
FROM alpine:3.24

RUN addgroup -S app && adduser -S app -G app -h /workspace -s /bin/sh

COPY --from=builder /usr/local/bin/tofu /usr/local/bin/tofu
COPY --chown=app:app ./init-auto-apply.sh /usr/local/bin/init-auto-apply.sh
RUN chmod +x /usr/local/bin/init-auto-apply.sh

WORKDIR /workspace
RUN chown -R app:app /workspace

RUN mkdir /tofu 
RUN chown -R app:app /tofu

ENV TF_IN_AUTOMATION=true
ENV TF_PLUGIN_CACHE_DIR=/tofu
ENV TF_DATA_DIR=/tofu

USER app
ENTRYPOINT ["/usr/local/bin/init-auto-apply.sh"]
