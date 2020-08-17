FROM java:8-jre

MAINTAINER wangiegie@gmail.com

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /pigx-pay-platform

WORKDIR /pigx-pay-platform

EXPOSE 5010

ADD ./target/pigx-pay-platform.jar ./

CMD sleep 180;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar pigx-pay-platform.jar
