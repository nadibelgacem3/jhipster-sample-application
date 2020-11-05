package org.pikasoft.mooin.mooincompanies;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.pikasoft.mooin.mooincompanies");

        noClasses()
            .that()
                .resideInAnyPackage("org.pikasoft.mooin.mooincompanies.service..")
            .or()
                .resideInAnyPackage("org.pikasoft.mooin.mooincompanies.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..org.pikasoft.mooin.mooincompanies.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
