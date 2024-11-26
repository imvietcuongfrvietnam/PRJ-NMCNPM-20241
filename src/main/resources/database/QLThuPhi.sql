-- SQL Server compatible script
CREATE DATABASE QLThuphi;
USE QLThuphi;
CREATE TABLE giadichvu (
                           TenDichVu nvarchar(32) NOT NULL,
                           ID int NOT NULL PRIMARY KEY,
                           DonGia decimal(18,2) NOT NULL,
                           ThongTinBoSung nvarchar(max)
);

CREATE TABLE quydonggop (
                            ID int PRIMARY KEY NOT NULL,
                            TenQuy nvarchar(128) NOT NULL,
                            MoTa nvarchar(max)
);

CREATE TABLE hodonggop (
                           MaHoDongGop nvarchar(8) NOT NULL,
                           MaQuy int NOT NULL,
                           SoTienDongGop decimal(18,2) NOT NULL,
                           ThongTinBoSung nvarchar(max),
                           ID int NOT NULL IDENTITY(1,1) PRIMARY KEY,
                           DotDongGop nvarchar(16) NOT NULL,
                           NgayDongGop date NOT NULL,
                           FOREIGN KEY (MaQuy) REFERENCES quydonggop(ID),
                           FOREIGN KEY (MaHoDongGop) REFERENCES hogiadinh(MaHoGiaDinh)
);

CREATE TABLE diennuocinternet (
                                  MaHoGiaDinh nvarchar(8) NOT NULL,
                                  MaKHDien nvarchar(10) NOT NULL PRIMARY KEY,
                                  MAKHNuoc nvarchar(10) NOT NULL UNIQUE,
                                  ThongTinBoSung nvarchar(max),
                                  MaKHInternet nvarchar(10) NOT NULL UNIQUE,
                                  NCCDien nvarchar(64) NOT NULL,
                                  NCCNuoc nvarchar(64) NOT NULL,
                                  NCCInternet nvarchar(64) NOT NULL,
                                  FOREIGN KEY (MaHoGiaDinh) REFERENCES hogiadinh(MaHoGiaDinh)
);
--DROP TABLE diennuocinternet;

CREATE TABLE giadichvuguixe (
                                LoaiXe nvarchar(32) PRIMARY KEY NOT NULL,
                                GiaGuiMotThang int NOT NULL
);

INSERT INTO giadichvuguixe VALUES ('Xe may',70000),('Xe o to',1200000);

CREATE TABLE hoadondichvu (
                              MaDichVu INT NOT NULL,
                              MaHD nvarchar(10) NOT NULL PRIMARY KEY,
                              MaHoGiaDinh nvarchar(8) NOT NULL,
                              SoTien decimal(18,2) NOT NULL,
                              NgayHetHan date NOT NULL,
                              ThongTinBoSung nvarchar(max),
                              FOREIGN KEY (MaHoGiaDinh) REFERENCES hogiadinh(MaHoGiaDinh),
                              FOREIGN KEY (MaDichVu) References giadichvu(ID)
);

--drop table hoadondichvu;

CREATE TABLE hoadondien (
                            MaHD nvarchar(10) NOT NULL PRIMARY KEY,
                            MaKH nvarchar(10) NOT NULL,
                            ChiSoDienSuDung decimal(18,2) NOT NULL,
                            NgayHetHan date NOT NULL,
                            ThongTinBoSung nvarchar(max),
                            TienDien decimal(18,2) NOT NULL,
                            FOREIGN KEY (MaKH) REFERENCES diennuocinternet(MaKHDien)
);
--drop table hoadondien;

CREATE TABLE hoadonguixe (
                             MaHD nvarchar(10) NOT NULL PRIMARY KEY,
                             SoTien decimal(18,2) NOT NULL,
                             BienSo nvarchar(32) not null,
                             NgayHetHan date NOT NULL,
                             ThongTinBoSung nvarchar(max),
                             FOREIGN KEY (BienSo) REFERENCES quanlyxe(BienSo)
);
--drop table hoadonguixe;

CREATE TABLE hoadoninternet (
                                MaHD nvarchar(10) PRIMARY KEY NOT NULL,
                                MaKH nvarchar(10) NOT NULL,
                                SoTien decimal(18,2) NOT NULL,
                                NgayHetHan date NOT NULL,
                                ThongTinBoSung nvarchar(max),
                                FOREIGN KEY (MaKH) REFERENCES diennuocinternet(MaKHInternet)
);

CREATE TABLE hoadonnuoc (
                            MaKH nvarchar(10) NOT NULL,
                            MaHD nvarchar(10) PRIMARY KEY NOT NULL,
                            NgayHetHan date NOT NULL,
                            ThongTinBoSung nvarchar(max),
                            TienNuoc decimal(18,2) NOT NULL,
                            FOREIGN KEY (MaKH) REFERENCES diennuocinternet(MaKHNuoc)
);

CREATE TABLE hogiadinh (
                           MaHoGiaDinh nvarchar(8) PRIMARY KEY NOT NULL,
                           MaPhongThue nvarchar(10) NOT NULL,
                           NgayChuyenVao date NOT NULL,
                           NgayChuyenRa date NOT NULL,
                           SoCMNDChuHo nvarchar(12) NOT NULL,
                           TrangThai nvarchar(32) NOT NULL,
                           FOREIGN KEY (MaPhongThue) REFERENCES phong(ID)
);
ALTER TABLE hogiadinh ADD CONSTRAINT FK_SocmndCHg foreign key (SoCMNDChuHo) REFERENCES nguoithue(SoCMND);

CREATE TABLE nguoithue (
                           SoCMND nvarchar(12) PRIMARY KEY NOT NULL,
                           GioiTinh nvarchar(8) NOT NULL,
                           NgaySinh date NOT NULL,
                           QueQuan nvarchar(255) NOT NULL,
                           HoTen nvarchar(255) NOT NULL,
                           NgheNghiep nvarchar(128) NOT NULL,
                           TrangThai nvarchar(32) NOT NULL,
                           DanToc nvarchar(32) NOT NULL,
                           QuocTich nvarchar(128) NOT NULL,
                           TrinhDoHocVan nvarchar(128) NOT NULL,
                           ThongTinBoSung nvarchar(max),
                           MaHoGiaDinh nvarchar(8) NOT NULL,
                           FOREIGN KEY (MaHoGiaDinh) REFERENCES hogiadinh(MaHoGiaDinh)
);

CREATE TABLE phong (
                       ID nvarchar(10) PRIMARY KEY NOT NULL,
                       Tang int NOT NULL,
                       DienTich int NOT NULL,
                       TinhTrang nvarchar(16) NOT NULL,
                       ThongTinBoSung nvarchar(max) NOT NULL
);

CREATE TABLE quanlyxe (
                          MaHo nvarchar(8) NOT NULL,
                          LoaiPhuongTien nvarchar(32) NOT NULL,
                          BienSo nvarchar(32) PRIMARY KEY NOT NULL,
                          ThongTinBoSung nvarchar(max),
                          NgayBatDau date NOT NULL,
                          NgayKetThuc date NOT NULL,
                          FOREIGN KEY (MaHo) REFERENCES hogiadinh(MaHoGiaDinh),
                          FOREIGN KEY (LoaiPhuongTien) REFERENCES giadichvuguixe(LoaiXe)
);

CREATE TABLE taikhoannguoidung (
                                   MaTaiKhoan int NOT NULL IDENTITY(1,1),
                                   VaiTro nvarchar(8) NOT NULL,
                                   TenDangNhap nvarchar(255) NOT NULL UNIQUE,
                                   MatKhau nvarchar(255) NOT NULL,
                                   NgayTaoTaiKhoan datetime NOT NULL,
                                   PRIMARY KEY (MaTaiKhoan)
);
--DROP TABLE taikhoannguoidung;

INSERT INTO taikhoannguoidung VALUES ('Admin','admin','123456','2024-10-13 15:46:38', '0'),('user','vietcuong04','123456','2024-10-13 15:47:48', '0');

CREATE TABLE thongtinnguoidung (
                                   MaTaiKhoan int NOT NULL,
                                   Ten nvarchar(255) NOT NULL,
                                   SoCMND nvarchar(16) NOT NULL PRIMARY KEY,
                                   NgaySinh date NOT NULL,
                                   Email nvarchar(255) NOT NULL,
                                   QueQuan nvarchar(255) NOT NULL,
                                   DienThoai nvarchar(16) NOT NULL,
                                   FOREIGN KEY (MaTaiKhoan) REFERENCES taikhoannguoidung(MaTaiKhoan)
);
--drop table thongtinnguoidung;
ALTER TABLE taikhoannguoidung ADD TinhTrang INT NOT NULL; --hien thi co dang nhap hay chua

INSERT INTO thongtinnguoidung VALUES
                                  (1,'Nguyen Van A','123456789123','2000-01-01','nva@gmail.com','Ho Chi Minh','0987654321'),
                                  (2,'Viet Cuong','123456789234','2000-09-25','vietcuong04@gmail.com','Nam Dinh','0399565455');


SELECT MAX(MaTaiKhoan) FROM taikhoannguoidung;