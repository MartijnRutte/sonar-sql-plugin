package org.antlr.sql.dialects.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.sql.dialects.Dialects;
import org.sonar.plugins.sql.models.rules.Rule;
import org.sonar.plugins.sql.models.rules.RuleDistanceIndexMatchType;
import org.sonar.plugins.sql.models.rules.RuleImplementation;
import org.sonar.plugins.sql.models.rules.RuleMatchType;
import org.sonar.plugins.sql.models.rules.RuleResultType;
import org.sonar.plugins.sql.models.rules.SqlRules;

public enum DB2ZSQLRules {

    INSTANCE;

    BaseRules baseRules = BaseRules.INSTANCE;

    public List<SqlRules> getRules() {
        List<SqlRules> rules = new ArrayList<>();
        {
            SqlRules customRules = new SqlRules();
            customRules.setRepoKey("SQLCC");
            customRules.setRepoName("SQL Plugin checks");
            customRules.setDialect(Dialects.DB2ZSQL.name());
            customRules.getRule().addAll(Arrays.asList(getGrantToPublicIsUsedRule()));
            rules.add(customRules);
        }
        return rules;
    }

    protected Rule getGrantToPublicIsUsedRule() {
        Rule rule = new Rule();
        rule.setKey("DCL0080");
        rule.setInternalKey("DCL0080");
        rule.setName("GRANT TO PUBLIC forbidden");
        rule.setDescription("GRANT TO PUBLIC is forbidden. Rewrite this statement.");
        rule.setTag("dcl");
        rule.setSeverity("MAJOR");
        rule.setRemediationFunction("LINEAR");
        rule.setDebtRemediationFunctionCoefficient("2min");
        rule.getRuleImplementation().setRuleViolationMessage("GRANT TO PUBLIC was found");
        rule.getRuleImplementation().getNames().getTextItem()
                .add(org.antlr.sql.dialects.db2z.DB2zSQLParser.GrantTableStatementContext.class.getSimpleName());
	rule.getRuleImplementation().getTextToFind().getTextItem().add("TO PUBLIC");
	rule.getRuleImplementation().setRuleMatchType(RuleMatchType.TEXT_AND_CLASS);
        rule.getRuleImplementation().setRuleResultType(RuleResultType.FAIL_IF_FOUND);
        rule.getRuleImplementation().getViolatingRulesCodeExamples().getRuleCodeExample()
                .add("GRANT SELECT ON DPS.TABELLETJE TO PUBLIC;");
        rule.getRuleImplementation().getCompliantRulesCodeExamples().getRuleCodeExample()
                .add("GRANT SELECT ON DPS.TABELLETJE TO APPUSER;");
        return rule;
    }

}
