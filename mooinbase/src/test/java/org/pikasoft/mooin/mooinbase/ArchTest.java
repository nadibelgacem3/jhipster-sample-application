package org.pikasoft.mooin.mooinbase;

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
            .importPackages("org.pikasoft.mooin.mooinbase");

        noClasses()
            .that()
                .resideInAnyPackage("org.pikasoft.mooin.mooinbase.service..")
            .or()
                .resideInAnyPackage("org.pikasoft.mooin.mooinbase.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..org.pikasoft.mooin.mooinbase.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
