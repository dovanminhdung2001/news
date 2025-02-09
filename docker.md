a. st
- VPS : chia máy chủ vật lý thành các máy chủ ảo

	- hosting :  cứng frame word
	- server: tùy chỉnh

	- ddz.name.vn

b. docker
- image = hđh + software
- container = running image
- registry = nơi chứa image ( phổ biến nhất là docker_host)


####################################################################

1. đóng gói, run thuần

   mvn package (cmd thuần)
   .mvnw package (có intelij)

   java -jar <file jar>

2. docker
   a. docker file:
      b1: build
         # maven 3.9.9 ; java 17
         FROM maven:3.9.9-amazoncorretto-17 AS build
         # copy source code and pom.xml to /app folder
         WORKDIR /app
         COPY pom.xml .
         COPY src ./src
         # build source code wwith maven  
         # (-DskipTests : option run mà k check, nhanh hơn nhưng k khuyến khích)
         RUN mvn package -DskipTests
         # set working folder to app and copy complied file from above step
         # (build: giá trị khai báo ở b1)
         WORKDIR /app
         COPY --from=build /app/target/*.jar app.jar 
         # COMMAND TO RUN APPLICATION
         ENTRYPOINT ["java", "jar", "app.jar"]
      b2: crate image
         FROM amazoncorretto:17 
         # set working folder to app and copy complied file from above step
         WORKDIR /app
         COPY --from=build /app/target/*.jar app.jar 
         # COMMAND TO RUN APPLICATION
         ENTRYPOINT ["java", "jar", "app.jar"]
    
   b.  vào cmd
      docker build -t <account>/<image-name>:<version> .		
      docker build -t  dovanminhdung2001@gmail.com/news-prj-zzz:0.0.1 .
        
      ### create network : docker network create <network-name>
      docker network create db-connect-network
      ### start db in network
      docker run -- network
      ### -e: biến môi trường :   cú pháp   ${name:defaultvalue}   ví dụ: spring.datasource.url=jdbc:mariadb://${IP:localhost}:3366/game-at
      docker run  --name container-news-prj -p 8080:8080 -e IP=192.168.2.161 -d dovaminhdung2001/news-prj-zz:0.0.2
    
      # b4 push docker image to Docker Hub
      ### update image:  push  lên
      docker image push <account>/<image-name>:<version>
      docker image push dovanminhdung2001@gmail/news-prj-zzz:0.0.1
    
      ### xóa image 
      docker image rm <image-name>:<version>  hoặc có thể có <account>
      ### xóa container
      docker rm <container-name>