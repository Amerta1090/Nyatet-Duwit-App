package com.nyatetduwit.domain.usecase.template;

import com.nyatetduwit.domain.repository.TemplateRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class ToggleTemplatePinUseCase_Factory implements Factory<ToggleTemplatePinUseCase> {
  private final Provider<TemplateRepository> repositoryProvider;

  public ToggleTemplatePinUseCase_Factory(Provider<TemplateRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ToggleTemplatePinUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ToggleTemplatePinUseCase_Factory create(
      Provider<TemplateRepository> repositoryProvider) {
    return new ToggleTemplatePinUseCase_Factory(repositoryProvider);
  }

  public static ToggleTemplatePinUseCase newInstance(TemplateRepository repository) {
    return new ToggleTemplatePinUseCase(repository);
  }
}
