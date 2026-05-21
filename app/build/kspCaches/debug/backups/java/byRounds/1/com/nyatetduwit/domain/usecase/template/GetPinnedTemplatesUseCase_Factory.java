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
public final class GetPinnedTemplatesUseCase_Factory implements Factory<GetPinnedTemplatesUseCase> {
  private final Provider<TemplateRepository> repositoryProvider;

  public GetPinnedTemplatesUseCase_Factory(Provider<TemplateRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetPinnedTemplatesUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetPinnedTemplatesUseCase_Factory create(
      Provider<TemplateRepository> repositoryProvider) {
    return new GetPinnedTemplatesUseCase_Factory(repositoryProvider);
  }

  public static GetPinnedTemplatesUseCase newInstance(TemplateRepository repository) {
    return new GetPinnedTemplatesUseCase(repository);
  }
}
