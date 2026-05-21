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
public final class UpdateTemplateUseCase_Factory implements Factory<UpdateTemplateUseCase> {
  private final Provider<TemplateRepository> repositoryProvider;

  public UpdateTemplateUseCase_Factory(Provider<TemplateRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UpdateTemplateUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UpdateTemplateUseCase_Factory create(
      Provider<TemplateRepository> repositoryProvider) {
    return new UpdateTemplateUseCase_Factory(repositoryProvider);
  }

  public static UpdateTemplateUseCase newInstance(TemplateRepository repository) {
    return new UpdateTemplateUseCase(repository);
  }
}
