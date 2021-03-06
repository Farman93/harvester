USE master;
GO
DROP DATABASE IntegratedDataRepository;
GO
CREATE DATABASE IntegratedDataRepository;
GO
USE IntegratedDataRepository;
IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = N'csconnector')
CREATE USER [csconnector] FOR LOGIN [csconnector] WITH DEFAULT_SCHEMA=[dbo]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Protocol]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Protocol](
	[id] [int] NOT NULL,
	[name] [nvarchar](max) NULL,
 CONSTRAINT [PK_Protocol] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[EnrichedDocument]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[EnrichedDocument](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[title] [nvarchar](max) NULL,
	[isbn] [nvarchar](max) NULL,
	[date_time] [datetime] NULL CONSTRAINT [DF__Document__date_t__7F60ED59]  DEFAULT (getdate()),
	[xml] [nvarchar](max) NULL,
	[enriched_id] [int] NULL
 CONSTRAINT [PK_EnrichedDocument] PRIMARY KEY CLUSTERED 
	([id] ASC
	)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
	) ON [PRIMARY]
END
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Document]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Document](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[title] [nvarchar](max) NULL,
	[isbn] [nvarchar](max) NULL,
	[protocol_id] [int] NULL,
	[date_time] [datetime] NULL CONSTRAINT [DF__Document__date_t__7F60ED60]  DEFAULT (getdate()),
	[url] [nvarchar](max) NULL,
	[xml] [nvarchar](max) NULL,
	[enriched_id] [int] NULL
 CONSTRAINT [PK_Document] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Document_Protocol]') AND parent_object_id = OBJECT_ID(N'[dbo].[Document]'))
ALTER TABLE [dbo].[Document]  WITH CHECK ADD  CONSTRAINT [FK_Document_Protocol] FOREIGN KEY([protocol_id])
REFERENCES [dbo].[Protocol] ([id])
GO
ALTER TABLE [dbo].[Document] CHECK CONSTRAINT [FK_Document_Protocol]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Document_EnrichedDocument]') AND parent_object_id = OBJECT_ID(N'[dbo].[Document]'))
ALTER TABLE [dbo].[Document]  WITH CHECK ADD  CONSTRAINT [FK_Document_EnrichedDocument] FOREIGN KEY([enriched_id])
REFERENCES [dbo].[EnrichedDocument] ([id])
GO
ALTER TABLE [dbo].[Document] CHECK CONSTRAINT [FK_Document_EnrichedDocument]
GO

INSERT INTO [dbo].[Protocol]
VALUES (1, 'aleph')
GO
INSERT INTO [dbo].[Protocol]
VALUES (2, 'sru')
GO
INSERT INTO [dbo].[Protocol]
VALUES (3, 'opac')
GO
INSERT INTO [dbo].[Protocol]
VALUES (4, 'marcsql')
GO

GRANT SELECT ON Document TO csconnector
GRANT SELECT ON EnrichedDocument TO csconnector
GRANT UPDATE ON Document TO csconnector
GRANT UPDATE ON EnrichedDocument To csconnector
GRANT INSERT ON Document TO csconnector
GRANT INSERT ON EnrichedDocument TO csconnector


select * from Document