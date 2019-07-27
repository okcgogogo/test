alter table COMMENT
	add content varchar2(1000);

comment on column COMMENT.content is '回复内容';