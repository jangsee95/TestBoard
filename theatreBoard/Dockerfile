# Dockerfile

# 1. 빌드 단계: Gradle을 사용하여 .war 파일 생성
FROM gradle:8.4-jdk17 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle build -x test

# 2. 실행 단계: Tomcat 서버에 빌드된 .war 파일 배포
FROM tomcat:10.1-jdk17-temurin
# Gradle 빌드 단계에서 생성된 .war 파일을 Tomcat의 webapps 폴더로 복사
COPY --from=build /home/gradle/src/build/libs/*.war /usr/local/tomcat/webapps/ROOT.war