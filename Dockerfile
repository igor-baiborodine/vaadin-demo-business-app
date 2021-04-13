################################################################################
# Build stage 0: builder
################################################################################
FROM maven:3-jdk-8 AS builder

WORKDIR /usr/src/app

COPY . .

RUN set -ex; \
    curl -sL https://deb.nodesource.com/setup_14.x | bash -;\
    apt-get install -y --no-install-recommends \
      nodejs; \
    node --version; \
    npm --version

RUN mvn clean --batch-mode package -Pproduction -DskipTests; \
    mv /usr/src/app/target/vaadin-demo-business-app-$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout).war \
        /usr/src/app/target/ROOT.war

################################################################################
# Build stage 1: actual image
################################################################################
FROM jetty:9-jre8-slim

LABEL \
    maintainer="Igor Baiborodine <igor@kiroule.com>" \
    org.label-schema.schema-version="1.0" \
    org.label-schema.name="vaadin-demo-business-app" \
    org.label-schema.vcs-url="https://github.com/igor-baiborodine/vaadin-demo-business-app" \
    org.label-schema.usage="https://github.com/igor-baiborodine/vaadin-demo-business-app/blob/master/README.md"

COPY --from=builder /usr/src/app/target/ROOT.war ${JETTY_BASE}/webapps/ROOT.war
