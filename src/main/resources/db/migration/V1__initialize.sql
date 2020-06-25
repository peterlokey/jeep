-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Jun 25, 2020 at 08:22 PM
-- Server version: 5.6.34-log
-- PHP Version: 7.1.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jeep-data`
--
CREATE DATABASE IF NOT EXISTS `jeep-data` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `jeep-data`;

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `date_time` datetime DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `event_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `id` int(11) NOT NULL,
  `date_time` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `formatted_date` varchar(255) DEFAULT NULL,
  `location` varchar(255) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(16) NOT NULL,
  `last_name` varchar(16) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user_event`
--

CREATE TABLE `user_event` (
  `user_id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
  ADD KEY `FKhr48nopy5aorw0ta1ii704tpu` (`event_id`);

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfnvc9jgli0tyfktl08q7h5rgd` (`creator_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`);

--
-- Indexes for table `user_event`
--
ALTER TABLE `user_event`
  ADD KEY `FKspe8srtv69gubpphvrnd7wekt` (`event_id`),
  ADD KEY `FKk3smcqwou8absq8qjt3wk4vy9` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
