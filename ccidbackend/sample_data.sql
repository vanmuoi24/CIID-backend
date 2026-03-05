-- ============================================
-- CCID Sample Data - Dữ liệu mẫu
-- ============================================
-- Thứ tự insert: Users -> Applications -> Documents -> LegalizationStamps
-- ============================================

-- ============================================
-- 1. USERS (5 users)
-- ============================================
-- Password: "password123" đã được mã hóa bằng BCrypt
-- Bạn có thể dùng: https://bcrypt-generator.com/ để tạo password mới

INSERT INTO users (username, password, full_name, role, created_at) VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Nguyễn Văn Admin', 'ADMIN', NOW()),
('user1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Trần Thị Hương', 'USER', NOW()),
('user2', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Lê Văn Minh', 'USER', NOW()),
('manager1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Phạm Thị Mai', 'MANAGER', NOW()),
('user3', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Hoàng Văn Tuấn', 'USER', NOW());

-- ============================================
-- 2. APPLICATIONS (5 applications)
-- ============================================

INSERT INTO applications (application_code, submission_method, receipt_date, competent_authority, result_date, certifying_signatory, signatory_title, status, created_at, updated_at, user_id) VALUES
('APP001', 'Trực tiếp', '2024-01-15', 'Sở Ngoại vụ Hà Nội', '2024-01-25', 'Nguyễn Văn A', 'Giám đốc Sở', 'COMPLETED', NOW(), NOW(), 2),
('APP002', 'Qua bưu điện', '2024-01-20', 'Sở Ngoại vụ TP.HCM', '2024-02-05', 'Trần Thị B', 'Phó Giám đốc', 'PROCESSING', NOW(), NOW(), 3),
('APP003', 'Trực tuyến', '2024-02-01', 'Sở Ngoại vụ Đà Nẵng', '2024-02-15', 'Lê Văn C', 'Trưởng phòng', 'APPROVED', NOW(), NOW(), 2),
('APP004', 'Trực tiếp', '2024-02-10', 'Sở Ngoại vụ Hải Phòng', '2024-02-20', 'Phạm Văn D', 'Giám đốc Sở', 'PENDING', NOW(), NOW(), 5),
('APP005', 'Trực tuyến', '2024-02-15', 'Sở Ngoại vụ Cần Thơ', '2024-02-28', 'Hoàng Thị E', 'Phó Giám đốc', 'PROCESSING', NOW(), NOW(), 3);

-- ============================================
-- 3. DOCUMENTS (5 documents)
-- ============================================

INSERT INTO documents (application_id, cv_type, document_title, document_type, holder_name, reference_number, issue_date, certifying_authority, certifying_signatory, certifying_title) VALUES
(1, 'CNLS', 'Bằng Đại học', 'Bản dịch', 'TRẦN THỊ HƯƠNG', '15364', '2024-01-10', 'VPCC Nguyễn Huệ, P. Ô Chợ Dừa, Q. Đống Đa, TP. Hà Nội', 'Lê Như Tuân', 'Công chứng viên'),
(2, 'HPLS', 'Giấy khai sinh', 'Bản chính', 'LÊ VĂN MINH', '28475', '2024-01-15', 'UBND Phường 1, Quận 3, TP. Hồ Chí Minh', 'Nguyễn Văn Phúc', 'Chủ tịch UBND'),
(3, 'CNLS', 'Bằng Cao đẳng', 'Bản sao', 'TRẦN THỊ HƯƠNG', '39586', '2024-01-25', 'VPCC Lê Lợi, Q. Hải Châu, TP. Đà Nẵng', 'Võ Thị Lan', 'Công chứng viên'),
(4, 'HPLS', 'Giấy chứng nhận kết hôn', 'Bản chính', 'HOÀNG VĂN TUẤN', '47697', '2024-02-05', 'UBND Phường Lê Chân, Q. Lê Chân, TP. Hải Phòng', 'Trần Văn Hùng', 'Chủ tịch UBND'),
(5, 'CNLS', 'Bằng Thạc sĩ', 'Bản dịch', 'LÊ VĂN MINH', '56708', '2024-02-10', 'VPCC Trần Phú, Q. Ninh Kiều, TP. Cần Thơ', 'Phan Thị Mai', 'Công chứng viên');

-- ============================================
-- 4. LEGALIZATION_STAMPS (5 stamps)
-- ============================================

INSERT INTO legalization_stamps (document_id, country, signed_by, signed_title, notary_office, certified_place, certified_date, consular_department, stamp_number, qr_code, signature_image) VALUES
(1, 'Việt Nam', 'Nguyễn Thị Lan Anh', 'Lãnh sự', 'Lãnh sự quán Việt Nam tại Mỹ', 'Washington D.C., Hoa Kỳ', '2024-01-20', 'Bộ Ngoại giao Việt Nam', 'LS-2024-001', 'QR001234567890', '/signatures/stamp001.png'),
(2, 'Việt Nam', 'Trần Văn Bình', 'Phó Lãnh sự', 'Lãnh sự quán Việt Nam tại Nhật', 'Tokyo, Nhật Bản', '2024-01-22', 'Bộ Ngoại giao Việt Nam', 'LS-2024-002', 'QR002345678901', '/signatures/stamp002.png'),
(3, 'Việt Nam', 'Lê Thị Cẩm', 'Lãnh sự trưởng', 'Lãnh sự quán Việt Nam tại Pháp', 'Paris, Pháp', '2024-01-28', 'Bộ Ngoại giao Việt Nam', 'LS-2024-003', 'QR003456789012', '/signatures/stamp003.png'),
(4, 'Việt Nam', 'Phạm Văn Dũng', 'Lãnh sự', 'Lãnh sự quán Việt Nam tại Úc', 'Sydney, Úc', '2024-02-08', 'Bộ Ngoại giao Việt Nam', 'LS-2024-004', 'QR004567890123', '/signatures/stamp004.png'),
(5, 'Việt Nam', 'Hoàng Thị Nga', 'Phó Lãnh sự trưởng', 'Lãnh sự quán Việt Nam tại Hàn Quốc', 'Seoul, Hàn Quốc', '2024-02-12', 'Bộ Ngoại giao Việt Nam', 'LS-2024-005', 'QR005678901234', '/signatures/stamp005.png');

-- ============================================
-- Kiểm tra dữ liệu đã insert
-- ============================================

-- SELECT * FROM users;
-- SELECT * FROM applications;
-- SELECT * FROM documents;
-- SELECT * FROM legalization_stamps;

-- ============================================
-- Thông tin đăng nhập test:
-- ============================================
-- Username: admin     | Password: password123 | Role: ADMIN
-- Username: user1     | Password: password123 | Role: USER
-- Username: user2     | Password: password123 | Role: USER
-- Username: manager1  | Password: password123 | Role: MANAGER
-- Username: user3     | Password: password123 | Role: USER
-- ============================================
