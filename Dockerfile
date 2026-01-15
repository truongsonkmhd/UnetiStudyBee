# syntax=docker/dockerfile:1.6

FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# 1) Copy các file build tool trước để Docker cache deps
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Fix permission + CRLF (nếu repo từng edit trên Windows)
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

# 2) Tải dependencies trước (cực quan trọng để lần sau build nhanh)
# Dùng cache ~/.m2 để không tải lại mỗi lần build
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -q -DskipTests dependency:go-offline

# 3) Sau đó mới copy source (thay đổi source không phá cache deps)
COPY src src

# 4) Build jar (cũng dùng cache ~/.m2)
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -q -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# App port + Debug port
EXPOSE 8097 5005

ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","-jar","app.jar"]
