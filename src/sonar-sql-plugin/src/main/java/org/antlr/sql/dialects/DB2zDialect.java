package org.antlr.sql.dialects;

import org.antlr.sql.dialects.db2z.DB2zSQLLexer;
import org.antlr.sql.dialects.db2z.DB2zSQLParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;

public class DB2zDialect extends BaseDialect {

	@Override
	protected Lexer getLexer(CharStream charStream) {
		return new DB2zSQLLexer(charStream);
	}

	@Override
	protected ParseTree getRoot(CommonTokenStream stream) {
		DB2zSQLParser p = new DB2zSQLParser(stream);
		p.removeErrorListeners();
		return p.startRule();
	}

	@Override
	protected DialectLanguageTypesMap getTypesMap() {
		return new DialectLanguageTypesMap().addCommentToken(DB2zSQLParser.COMMENT)
				.addCommentToken(DB2zSQLParser.SQLCOMMENT).addStringToken(DB2zSQLParser.NONNUMERICLITERAL);
	}
}
