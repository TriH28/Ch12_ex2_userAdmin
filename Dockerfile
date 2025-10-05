# ----------- BẢN DOCKERFILE CHUẨN CHO TOMCAT + WAR -------------
# Sử dụng image Tomcat chính thức (có sẵn Java)
FROM tomcat:9.0-jdk17-temurin

# Xóa app mặc định (ROOT webapp mặc định của Tomcat)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file WAR của bạn vào Tomcat webapps/
COPY ch12_ex2_userAdmin_war.war /usr/local/tomcat/webapps/ROOT.war

# Thiết lập biến môi trường cho PostgreSQL (Render DB)
# → Bạn có thể ghi tạm hoặc set trong Render Dashboard (Environment)
ENV DB_URL=jdbc:postgresql://dpg-d360ghvdiees738n9bfg-a.singapore-postgres.render.com/murachdb_qgsx
ENV DB_USER=murachdb
ENV DB_PASSWORD=hYGAYuCZPyhAC910QZcD3A8eytGj97bk

# Mở port 8080 cho web
EXPOSE 8080

# Lệnh chạy Tomcat khi container khởi động
CMD ["catalina.sh", "run"]
