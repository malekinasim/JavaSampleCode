FROM eclipse-temurin:24-jdk

WORKDIR /app

COPY src ./src

RUN javac -d out $(find src -name "*.java")

CMD ["java", "-cp", "out", "nasim.com.example.RandomNumber"]