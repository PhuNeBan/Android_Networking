CREATE DATABASE IF NOT EXISTS PS19302_DoHoangPhu;

USE PS19302_DoHoangPhu

--TAO BANG
CREATE TABLE IF NOT EXISTS `users`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `email` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL,
    `phone` varchar(255) NOT NULL,
    `address` varchar(255) NOT NULL,
    `role` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`)
) ENGiNE = InnoDB DEFAULT CHARSET=utf8mb4;

--Tao bang topics (id, name, description)
CREATE TABLE IF NOT EXISTS `topics`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `description` varchar(255) NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT current_timestamp(),
    `updated_at` DATETIME NOT NULL DEFAULT current_timestamp(),
    `created_by` int(11) NOT NULL,
    `updated_by` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `created_by` (`created_by`),
    KEY `updated_by` (`updated_by`),
    CONSTRAINT `topics_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`),
    CONSTRAINT `topics_ibfk_2` FOREIGN KEY (`updated_by`) REFERENCES `users` (`id`)
) ENGiNE = InnoDB DEFAULT CHARSET=utf8mb4;

--Tao bang news (id, title, created_at, content, author_id, topic_id)
CREATE TABLE IF NOT EXISTS `news`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `title` nvarchar(255) NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT current_timestamp(),
    `updated_at` DATETIME NOT NULL DEFAULT current_timestamp(),
    `updated_by` int(11) NOT NULL,
    `content` nvarchar(255) NOT NULL,
    `image` varchar(255) NULL,
    `author_id` int(11) NOT NULL,
    `topic_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `author_id` (`author_id`),
    KEY `topic_id` (`topic_id`),
    KEY `updated_by` (`updated_by`),
    CONSTRAINT `news_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`),
    CONSTRAINT `news_ibfk_2` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`),
    CONSTRAINT `news_ibfk_3` FOREIGN KEY (`updated_by`) REFERENCES `users` (`id`)
) ENGiNE = InnoDB DEFAULT CHARSET=utf8mb4;

-- them du lieu vao bang users
INSERT INTO `users` (`id`, `email`, `password`, `name`, `phone`, `address`, `role`) VALUES
(1, 'abc@gmail.com', '123456', 'Nguyen Van A', '0123456789', 'Ha Noi', 'admin'),
(2, 'admin@gmail.com', '23456', 'Nguyen Van B', '0123456789', 'Ha Noi', 'admin');

-- them du lieu vao bang topics
INSERT INTO `topics` (`id`, `name`, `description`, `updated_by`, `created_by`) VALUES
(1, 'The thao', 'The thao', 1, 1),
(2, 'Giao duc', 'Giao duc', 1, 1),
(3, 'Kinh doanh', 'Kinh doanh', 1, 1);

-- them du lieu vao bang news
INSERT INTO `news` (`id`, `title`, `updated_by`, `content`, `author_id`, `topic_id`) VALUES
(1, 'Tin tuc 1', 1, 'Noi dung tin tuc 1', 1, 1),
(2, 'Tin tuc 2', 1, 'Noi dung tin tuc 2', 1, 2),
(3, 'Tin tuc 3', 1, 'Noi dung tin tuc 3', 1, 3),
(4, 'Tin tuc 4', 1, 'Noi dung tin tuc 4', 1, 1),
(5, 'Tin tuc 5', 1, 'Noi dung tin tuc 5', 1, 2),
(6, 'Tin tuc 6', 1, 'Noi dung tin tuc 6', 1, 3),
(7, 'Tin tuc 7', 1, 'Noi dung tin tuc 7', 1, 1),
(8, 'Tin tuc 8', 1, 'Noi dung tin tuc 8', 1, 2),
(9, 'Tin tuc 9', 1, 'Noi dung tin tuc 9', 1, 3),
(10, 'Tin tuc 10', 1, 'Noi dung tin tuc 10', 1, 1),
(11, 'Tin tuc 11', 1, 'Noi dung tin tuc 11', 1, 2),
(12, 'Tin tuc 12', 1, 'Noi dung tin tuc 12', 1, 3);

--fogot password
CREATE TABLE IF NOT EXISTS `password_resets`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `email` varchar(255) NOT NULL,
    `token` varchar(255) NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT current_timestamp(),
    `available` tinyint(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (`id`)
) ENGiNE = InnoDB DEFAULT CHARSET=utf8mb4;
