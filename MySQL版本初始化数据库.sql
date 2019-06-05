CREATE DATABASE IF NOT EXISTS `AppVote` DEFAULT CHARACTER SET utf8;

USE AppVote;

CREATE TABLE `Vote` (
  `VoteId` int(11) NOT NULL AUTO_INCREMENT,
  `VoteCreateUserId` varchar(40) NOT NULL,
  `VoteTitle` varchar(32) DEFAULT NULL,
  `VoteDescription` varchar(256) DEFAULT NULL,
  `VoteEndTime` bigint(20) DEFAULT NULL,
  `VoteType` tinyint(1) DEFAULT NULL,
  `VoteVisible` tinyint(1) NOT NULL,
  `VoteStatus` tinyint(1) NOT NULL,
  `VoteUserCounts` int(11) NOT NULL,
  `VoteCreateTime` bigint(20) NOT NULL,
  `ContainerType` varchar(16) DEFAULT NULL,
  `ContainerId` int(11) DEFAULT NULL,
  `ContainerName` varchar(32) DEFAULT NULL,
  `AppCode` varchar(16) DEFAULT NULL,
  `AppId` int(11) DEFAULT NULL,
  `AppName` varchar(100) DEFAULT NULL,
  `UnitId` int(11) DEFAULT NULL,
  `UnitPath` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`VoteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `VoteItem` (
  `VoteItemId` int(11) NOT NULL AUTO_INCREMENT,
  `VoteId` int(11) NOT NULL,
  `VoteItemName` varchar(40) NOT NULL,
  `VoteUserCounts` int(11) NOT NULL DEFAULT '0',
  `VoteItemIndex` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`VoteItemId`),
  KEY `IX_VoteItem_VoteId` (`VoteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `VoteUser` (
  `VoteUserId` int(11) NOT NULL AUTO_INCREMENT,
  `VoteId` int(11) NOT NULL,
  `UserId` varchar(40) NOT NULL,
  `VoteItemId` varchar(128) NOT NULL,
  `VoteCreateTime` bigint(20) NOT NULL,
  `VoteUserIP` varchar(50) DEFAULT NULL,
  `UnitId` int(11) DEFAULT NULL,
  `UnitPath` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`VoteUserId`),
  KEY `IX_VoteUser_VoteId` (`VoteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
