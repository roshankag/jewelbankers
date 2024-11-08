package com.jewelbankers.configuration;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class PhysicalNamingStrategySnakeCaseImpl extends PhysicalNamingStrategyStandardImpl {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 @Override
	    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
	        String snakeCaseName = toSnakeCase(name.getText());
	        return Identifier.toIdentifier(snakeCaseName);
	 }
	 
    private String toSnakeCase(String name) {
        // Convert camelCase to snake_case
        StringBuilder result = new StringBuilder();
        for (char c : name.toCharArray()) {
            if (Character.isUpperCase(c)) {
                if (result.length() != 0) {
                    result.append("_");
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
