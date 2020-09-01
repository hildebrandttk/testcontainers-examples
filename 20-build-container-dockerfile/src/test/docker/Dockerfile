FROM postgres:11
COPY create-user-schema.sql /docker-entrypoint-initdb.d
ENV POSTGRES_DB=userdb \
    POSTGRES_USER=userdb \
    POSTGRES_PASSWORD=test1234