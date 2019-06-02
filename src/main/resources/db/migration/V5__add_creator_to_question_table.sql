alter table QUESTION
	add creator int;

comment on column QUESTION.creator is '创建人id';