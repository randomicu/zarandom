DROP TABLE IF EXISTS "meta_info";
DROP SEQUENCE IF EXISTS meta_info_id_seq;
CREATE SEQUENCE meta_info_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1;

CREATE TABLE "public"."meta_info" (
    "id" integer DEFAULT nextval('meta_info_id_seq') NOT NULL,
    "username" varchar NOT NULL,
    "command" varchar NOT NULL,
    "user_text" varchar,
    "user_id" integer NOT NULL,
    "channel_id" integer NOT NULL,
    "channel_name" varchar NOT NULL,
    "team_id" integer NOT NULL,
    "team_domain" varchar NOT NULL,
    "create_date" date
) WITH (oids = false);

COMMENT ON TABLE "public"."meta_info" IS 'Bot will save all meta information here';
COMMENT ON COLUMN "public"."meta_info"."id" IS 'Inner id column';
COMMENT ON COLUMN "public"."meta_info"."username" IS 'Username in Slack';
COMMENT ON COLUMN "public"."meta_info"."command" IS 'Command from Slack';
COMMENT ON COLUMN "public"."meta_info"."user_text" IS 'User text from Slack (nullable)';
COMMENT ON COLUMN "public"."meta_info"."user_id" IS 'User id in Slack';
COMMENT ON COLUMN "public"."meta_info"."channel_id" IS 'Channel id in Slack';
COMMENT ON COLUMN "public"."meta_info"."channel_name" IS 'Channel name from Slack';
COMMENT ON COLUMN "public"."meta_info"."team_id" IS 'Team id in Slack';
COMMENT ON COLUMN "public"."meta_info"."team_domain" IS 'Team domain in Slack';
COMMENT ON COLUMN "public"."meta_info"."create_date" IS 'Row creation date';
