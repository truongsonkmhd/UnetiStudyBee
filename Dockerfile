FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Fix permission + (nếu cần) fix CRLF
RUN chmod +x mvnw \
  && sed -i 's/\r$//' mvnw \
  && ./mvnw -q -DskipTests=true package

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# App port + Debug port
EXPOSE 8080 5005

# Remote Debug (JDWP) bật trên 5005
# suspend=n: app chạy luôn; đổi thành y nếu muốn chờ attach debugger rồi mới chạy
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "app.jar"]
