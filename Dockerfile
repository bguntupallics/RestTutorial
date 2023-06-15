FROM eclipse-temurin:17.0.7_7-jdk-jammy as bulid
WORKDIR application

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw  install -DskipTests

RUN cp /application/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# Deployment layer
FROM eclipse-temurin:17.0.7_7-jdk-jammy
WORKDIR application

# Add a non-root user user an
#RUN addgroup --system appuser && adduser -S -s /usr/sbin/nologin -G appuser appuser
#USER appuser

COPY --from=bulid application/dependencies/ ./
COPY --from=bulid application/spring-boot-loader/ ./
COPY --from=bulid application/snapshot-dependencies/ ./
COPY --from=bulid application/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]