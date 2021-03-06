begin transaction
USE master;
GO
CREATE DATABASE [IntegratedDataRepository];
GO
USE IntegratedDataRepository
GO
CREATE LOGIN [csconnector] WITH PASSWORD = 'csconnector'
/****** Object:  User [csconnector]    Script Date: 09/17/2014 16:01:51 ******/
CREATE USER [csconnector] FOR LOGIN [csconnector] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  Table [dbo].[Protocol]    Script Date: 09/17/2014 16:01:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Protocol](
	[id] [int] NOT NULL,
	[name] [nvarchar](max) NULL,
 CONSTRAINT [PK_Protocol] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Document]    Script Date: 09/17/2014 16:01:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Document](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[protocol_id] [int] NULL,
	[date_time] [datetime] NULL CONSTRAINT [DF__Document__date_t__7F60ED59]  DEFAULT (getdate()),
	[url] [nvarchar](max) NULL,
	[xml] [nvarchar](max) NULL,
	[blob] [varbinary](max) NULL,
 CONSTRAINT [PK_Document] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  ForeignKey [FK_Document_Protocol]    Script Date: 09/17/2014 16:01:51 ******/
ALTER TABLE [dbo].[Document]  WITH CHECK ADD  CONSTRAINT [FK_Document_Protocol] FOREIGN KEY([protocol_id])
REFERENCES [dbo].[Protocol] ([id])
GO
ALTER TABLE [dbo].[Document] CHECK CONSTRAINT [FK_Document_Protocol]
GO
commit