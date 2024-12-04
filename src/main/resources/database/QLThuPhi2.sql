USE QLThuPhi;
-- 9. Tạo bảng tài khoản người dùng và thông tin người dùng
CREATE TABLE taikhoannguoidung (
  MaTaiKhoan int NOT NULL IDENTITY(1,1),
  VaiTro nvarchar(8) NOT NULL,
  TenDangNhap nvarchar(255) NOT NULL UNIQUE,
  MatKhau nvarchar(255) NOT NULL,
  NgayTaoTaiKhoan datetime NOT NULL,
  TinhTrang INT NOT NULL, -- Hiển thị trạng thái đăng nhập
  PRIMARY KEY (MaTaiKhoan)
);

CREATE TABLE thongtinnguoidung (
  MaTaiKhoan int NOT NULL,
  HoTen nvarchar(255) NOT NULL,
  SoCMND nvarchar(16) NOT NULL PRIMARY KEY,
  NgaySinh date NOT NULL,
  Email nvarchar(255) NOT NULL,
  QueQuan nvarchar(255) NOT NULL,
  DienThoai nvarchar(16) NOT NULL,
  FOREIGN KEY (MaTaiKhoan) REFERENCES taikhoannguoidung(MaTaiKhoan)
);
-- 1. Tạo bảng độc lập, không có khóa ngoại
CREATE TABLE canho (
  MaCanHo nvarchar(10) PRIMARY KEY NOT NULL,
  Tang int NOT NULL,
  DienTich int NOT NULL,
  TinhTrang nvarchar(16) NOT NULL,
  ThongTinBoSung nvarchar(max) NOT NULL
);

CREATE TABLE giadichvu (
  TenDichVu nvarchar(32) NOT NULL,
  ID int NOT NULL PRIMARY KEY,
  DonGia decimal(18,2) NOT NULL,
  ThongTinBoSung nvarchar(max)
);

CREATE TABLE giadichvuguixe (
  LoaiXe nvarchar(32) PRIMARY KEY NOT NULL,
  GiaGuiMotThang int NOT NULL
);

CREATE TABLE quydonggop (
  ID int PRIMARY KEY NOT NULL,
  TenQuy nvarchar(128) NOT NULL,
  MoTa nvarchar(max)
);



-- 2. Tạo bảng liên quan đến người thuê (phụ thuộc sau này)
CREATE TABLE cudan (
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
  MaHoGiaDinh nvarchar(8) NOT NULL -- Sẽ tham chiếu đến bảng `hogiadinh` sau
);

-- 3. Tạo bảng `hogiadinh`, tham chiếu đến `phong`
CREATE TABLE hogiadinh (
  MaHoGiaDinh nvarchar(8) PRIMARY KEY NOT NULL,
  MaCanHo nvarchar(10) NOT NULL,
  NgayChuyenVao date NOT NULL,
  NgayChuyenRa date,
  SoCMNDChuHo nvarchar(12) NOT NULL,
  TrangThai nvarchar(32) NOT NULL,
  FOREIGN KEY (MaCanHo) REFERENCES canho(MaCanHo)
);

-- 4. Thêm khóa ngoại từ `hogiadinh` tham chiếu đến `nguoithue`
ALTER TABLE hogiadinh 
ADD CONSTRAINT FK_SocmndCHg FOREIGN KEY (SoCMNDChuHo) REFERENCES cudan(SoCMND);

-- 5. Tạo bảng `diennuocinternet`, tham chiếu đến `hogiadinh`
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
-- 8. Tạo bảng quản lý xe
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


-- 6. Tạo bảng liên quan đến đóng góp
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

-- 7. Tạo bảng liên quan đến dịch vụ và hóa đơn
CREATE TABLE hoadondichvu (
  MaDichVu INT NOT NULL,
  MaHD nvarchar(10) NOT NULL PRIMARY KEY,
  MaHoGiaDinh nvarchar(8) NOT NULL,
  SoTien decimal(18,2) NOT NULL,
  NgayHetHan date NOT NULL,
  ThongTinBoSung nvarchar(max),
  FOREIGN KEY (MaHoGiaDinh) REFERENCES hogiadinh(MaHoGiaDinh),
  FOREIGN KEY (MaDichVu) REFERENCES giadichvu(ID)
);

CREATE TABLE hoadondien (
  MaHD nvarchar(10) NOT NULL PRIMARY KEY,
  MaKH nvarchar(10) NOT NULL,
  ChiSoDienSuDung decimal(18,2) NOT NULL,
  NgayHetHan date NOT NULL,
  ThongTinBoSung nvarchar(max),
  TienDien decimal(18,2) NOT NULL,
  FOREIGN KEY (MaKH) REFERENCES diennuocinternet(MaKHDien)
);

CREATE TABLE hoadonnuoc (
  MaKH nvarchar(10) NOT NULL,
  MaHD nvarchar(10) PRIMARY KEY NOT NULL,
  NgayHetHan date NOT NULL,
  ThongTinBoSung nvarchar(max),
  TienNuoc decimal(18,2) NOT NULL,
  FOREIGN KEY (MaKH) REFERENCES diennuocinternet(MaKHNuoc)
);

CREATE TABLE hoadoninternet (
  MaHD nvarchar(10) PRIMARY KEY NOT NULL,
  MaKH nvarchar(10) NOT NULL,
  SoTien decimal(18,2) NOT NULL,
  NgayHetHan date NOT NULL,
  ThongTinBoSung nvarchar(max),
  FOREIGN KEY (MaKH) REFERENCES diennuocinternet(MaKHInternet)
);

CREATE TABLE hoadonguixe (
  MaHD nvarchar(10) NOT NULL PRIMARY KEY,
  SoTien decimal(18,2) NOT NULL,
  BienSo nvarchar(32) NOT NULL,
  NgayHetHan date NOT NULL,
  ThongTinBoSung nvarchar(max),
  FOREIGN KEY (BienSo) REFERENCES quanlyxe(BienSo)
);


