DROP TABLE "ZUG" cascade constraints;

CREATE TABLE "ZUG" 
   (	"ZID" NUMBER NOT NULL ENABLE, 
	 CONSTRAINT "ZUG_PK" PRIMARY KEY ("ZID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOCOMPRESS LOGGING
    ENABLE
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
   ;
 
