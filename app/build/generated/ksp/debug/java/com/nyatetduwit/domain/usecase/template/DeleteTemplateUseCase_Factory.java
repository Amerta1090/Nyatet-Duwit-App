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
public final class DeleteTemplateUseCase_Factory implements Factory<DeleteTemplateUseCase> {
  private final Provider<TemplateRepository> repositoryProvider;

  public DeleteTemplateUseCase_Factory(Provider<TemplateRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeleteTemplateUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeleteTemplateUseCase_Factory create(
      Provider<TemplateRepository> repositoryProvider) {
    return new DeleteTemplateUseCase_Factory(repositoryProvider);
  }

  public static DeleteTemplateUseCase newInstance(TemplateRepository repository) {
    return new DeleteTemplateUseCase(repository);
  }
}
