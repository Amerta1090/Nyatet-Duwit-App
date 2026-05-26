package com.nyatetduwit.domain.usecase.transaction;

import com.nyatetduwit.domain.repository.TransactionTagRepository;
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
public final class SaveTagsUseCase_Factory implements Factory<SaveTagsUseCase> {
  private final Provider<TransactionTagRepository> repositoryProvider;

  public SaveTagsUseCase_Factory(Provider<TransactionTagRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SaveTagsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SaveTagsUseCase_Factory create(
      Provider<TransactionTagRepository> repositoryProvider) {
    return new SaveTagsUseCase_Factory(repositoryProvider);
  }

  public static SaveTagsUseCase newInstance(TransactionTagRepository repository) {
    return new SaveTagsUseCase(repository);
  }
}
