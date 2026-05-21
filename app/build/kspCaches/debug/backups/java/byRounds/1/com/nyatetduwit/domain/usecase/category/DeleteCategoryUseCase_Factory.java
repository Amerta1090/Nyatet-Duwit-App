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
public final class DeleteCategoryUseCase_Factory implements Factory<DeleteCategoryUseCase> {
  private final Provider<CategoryRepository> repositoryProvider;

  public DeleteCategoryUseCase_Factory(Provider<CategoryRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeleteCategoryUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeleteCategoryUseCase_Factory create(
      Provider<CategoryRepository> repositoryProvider) {
    return new DeleteCategoryUseCase_Factory(repositoryProvider);
  }

  public static DeleteCategoryUseCase newInstance(CategoryRepository repository) {
    return new DeleteCategoryUseCase(repository);
  }
}
