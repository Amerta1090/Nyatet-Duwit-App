package com.nyatetduwit.domain.usecase.category;

import com.nyatetduwit.domain.repository.CategoryRepository;
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
public final class UpdateCategoryUseCase_Factory implements Factory<UpdateCategoryUseCase> {
  private final Provider<CategoryRepository> repositoryProvider;

  public UpdateCategoryUseCase_Factory(Provider<CategoryRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UpdateCategoryUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UpdateCategoryUseCase_Factory create(
      Provider<CategoryRepository> repositoryProvider) {
    return new UpdateCategoryUseCase_Factory(repositoryProvider);
  }

  public static UpdateCategoryUseCase newInstance(CategoryRepository repository) {
    return new UpdateCategoryUseCase(repository);
  }
}
